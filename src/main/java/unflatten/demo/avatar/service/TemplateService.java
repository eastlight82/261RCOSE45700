package unflatten.demo.avatar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import unflatten.demo.avatar.api.ApiDtos;
import unflatten.demo.avatar.domain.TemplateEntity;
import unflatten.demo.avatar.repo.TemplateRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TemplateService implements CommandLineRunner {

    private final TemplateRepository templateRepository;
    private final ObjectMapper objectMapper;

    public TemplateService(TemplateRepository templateRepository, ObjectMapper objectMapper) {
        this.templateRepository = templateRepository;
        this.objectMapper = objectMapper;
    }

    public List<ApiDtos.TemplateResponse> listTemplates() {
        return templateRepository.findByActiveTrueOrderByNameAsc().stream().map(this::toResponse).toList();
    }

    public ApiDtos.TemplateResponse getTemplate(String templateId) {
        TemplateEntity template = templateRepository.findById(templateId)
            .orElseThrow(() -> new NoSuchElementException("Template not found"));
        return toResponse(template);
    }

    private ApiDtos.TemplateResponse toResponse(TemplateEntity e) {
        return new ApiDtos.TemplateResponse(
            e.getId(),
            e.getName(),
            e.getDescription(),
            e.getThumbnailUrl(),
            e.getVrmUrl(),
            toJsonNode(e.getDefaultValuesJson()),
            toStringList(e.getTagsJson())
        );
    }

    private JsonNode toJsonNode(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Stored template JSON is invalid", ex);
        }
    }

    private List<String> toStringList(String json) {
        if (json == null || json.isBlank()) return List.of();
        try {
            return objectMapper.readerForListOf(String.class).readValue(json);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Stored tags JSON is invalid", ex);
        }
    }

    @Override
    public void run(String... args) {
        if (templateRepository.count() > 0) return;

        TemplateEntity template = new TemplateEntity();
        template.setId("customizable-default");
        template.setName("Customizable Default");
        template.setDescription("Default template for avatar customization");
        template.setThumbnailUrl("/thumbnails/template-default.png");
        template.setVrmUrl("/models/CustomizableCharacter.vrm");
        template.setDefaultValuesJson("{}");
        template.setTagsJson("[]");
        template.setActive(true);
        templateRepository.save(template);
    }
}
