package unflatten.demo.avatar.service;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unflatten.demo.avatar.api.ApiDtos;
import unflatten.demo.avatar.domain.AvatarEntity;
import unflatten.demo.avatar.domain.AvatarVersionEntity;
import unflatten.demo.avatar.repo.AvatarRepository;
import unflatten.demo.avatar.repo.AvatarVersionRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AvatarService {

    private static final int MAX_VERSIONS = 5;

    private final AvatarRepository avatarRepository;
    private final AvatarVersionRepository versionRepository;
    private final ObjectMapper objectMapper;

    public AvatarService(AvatarRepository avatarRepository,
                         AvatarVersionRepository versionRepository,
                         ObjectMapper objectMapper) {
        this.avatarRepository = avatarRepository;
        this.versionRepository = versionRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ApiDtos.AvatarRecord saveAvatar(String avatarId, ApiDtos.SaveAvatarRequest req) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId).orElseGet(AvatarEntity::new);
        avatar.setAvatarId(avatarId);
        avatar.setTemplateId(req.templateId());
        avatar.setParametersJson(toJson(req.parameters()));
        AvatarEntity saved = avatarRepository.save(avatar);
        return toAvatarRecord(saved);
    }

    public ApiDtos.AvatarRecord loadAvatar(String avatarId) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId)
            .orElseThrow(() -> new NoSuchElementException("Avatar not found"));
        return toAvatarRecord(avatar);
    }

    public List<ApiDtos.AvatarRecord> listAvatars() {
        return avatarRepository.findAll().stream().map(this::toAvatarRecord).toList();
    }

    @Transactional
    public void deleteAvatar(String avatarId) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId)
            .orElseThrow(() -> new NoSuchElementException("Avatar not found"));
        versionRepository.deleteByAvatar(avatar);
        avatarRepository.delete(avatar);
    }

    @Transactional
    public void saveVersion(String avatarId, ApiDtos.AvatarVersionRequest req) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId)
            .orElseThrow(() -> new NoSuchElementException("Avatar not found"));

        AvatarVersionEntity version = versionRepository.findByAvatarAndVersionKey(avatar, req.id())
            .orElseGet(AvatarVersionEntity::new);

        version.setAvatar(avatar);
        version.setVersionKey(req.id());
        version.setName(req.name());
        version.setParametersJson(toJson(req.parameters()));
        version.setThumbnailDataUrl(req.thumbnailDataUrl());
        versionRepository.save(version);

        List<AvatarVersionEntity> versions = versionRepository.findByAvatarOrderByCreatedAtAsc(avatar);
        while (versions.size() > MAX_VERSIONS) {
            versionRepository.delete(versions.remove(0));
        }
    }

    public List<ApiDtos.AvatarVersionResponse> listVersions(String avatarId) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId)
            .orElseThrow(() -> new NoSuchElementException("Avatar not found"));
        return versionRepository.findByAvatarOrderByCreatedAtAsc(avatar).stream().map(this::toVersionResponse).toList();
    }

    @Transactional
    public void deleteVersion(String avatarId, String versionId) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId)
            .orElseThrow(() -> new NoSuchElementException("Avatar not found"));
        AvatarVersionEntity version = versionRepository.findByAvatarAndVersionKey(avatar, versionId)
            .orElseThrow(() -> new NoSuchElementException("Version not found"));
        versionRepository.delete(version);
    }

    @Transactional
    public void updateVersionName(String avatarId, String versionId, String name) {
        AvatarEntity avatar = avatarRepository.findByAvatarId(avatarId)
            .orElseThrow(() -> new NoSuchElementException("Avatar not found"));
        AvatarVersionEntity version = versionRepository.findByAvatarAndVersionKey(avatar, versionId)
            .orElseThrow(() -> new NoSuchElementException("Version not found"));
        version.setName(name);
        versionRepository.save(version);
    }

    private ApiDtos.AvatarRecord toAvatarRecord(AvatarEntity e) {
        return new ApiDtos.AvatarRecord(
            e.getAvatarId(),
            e.getTemplateId(),
            toJsonNode(e.getParametersJson()),
            e.getUpdatedAt()
        );
    }

    private ApiDtos.AvatarVersionResponse toVersionResponse(AvatarVersionEntity e) {
        return new ApiDtos.AvatarVersionResponse(
            e.getVersionKey(),
            e.getName(),
            toJsonNode(e.getParametersJson()),
            e.getThumbnailDataUrl(),
            e.getCreatedAt().toString()
        );
    }

    private String toJson(JsonNode node) {
        try {
            return objectMapper.writeValueAsString(node == null ? objectMapper.createObjectNode() : node);
        } catch (JacksonException ex) {
            throw new IllegalArgumentException("Invalid JSON payload", ex);
        }
    }

    private JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JacksonException ex) {
            throw new IllegalStateException("Stored JSON is invalid", ex);
        }
    }
}
