package unflatten.demo.avatar.api;

import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.List;

public final class ApiDtos {

    private ApiDtos() {
    }

    public record SaveAvatarRequest(
        String templateId,
        JsonNode parameters
    ) {
    }

    public record AvatarRecord(
        String avatarId,
        String templateId,
        JsonNode parameters,
        Instant updatedAt
    ) {
    }

    public record AvatarVersionRequest(
        String id,
        String name,
        JsonNode parameters,
        String thumbnailDataUrl,
        String createdAt
    ) {
    }

    public record AvatarVersionResponse(
        String id,
        String name,
        JsonNode parameters,
        String thumbnailDataUrl,
        String createdAt
    ) {
    }

    public record UpdateVersionNameRequest(
        String name
    ) {
    }

    public record TemplateResponse(
        String id,
        String name,
        String description,
        String thumbnailUrl,
        String vrmUrl,
        JsonNode defaultValues,
        List<String> tags
    ) {
    }

    public record ErrorResponse(
        String code,
        String message,
        String path,
        Instant timestamp
    ) {
    }
}
