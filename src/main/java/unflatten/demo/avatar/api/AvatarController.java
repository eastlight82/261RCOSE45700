package unflatten.demo.avatar.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unflatten.demo.avatar.service.AvatarService;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PutMapping("/avatars/{avatarId}")
    public ApiDtos.AvatarRecord saveAvatar(@PathVariable String avatarId,
                                           @RequestBody ApiDtos.SaveAvatarRequest req) {
        return avatarService.saveAvatar(avatarId, req);
    }

    @GetMapping("/avatars/{avatarId}")
    public ApiDtos.AvatarRecord loadAvatar(@PathVariable String avatarId) {
        return avatarService.loadAvatar(avatarId);
    }

    @GetMapping("/avatars")
    public List<ApiDtos.AvatarRecord> listAvatars() {
        return avatarService.listAvatars();
    }

    @DeleteMapping("/avatars/{avatarId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatar(@PathVariable String avatarId) {
        avatarService.deleteAvatar(avatarId);
    }

    @PostMapping("/avatars/{avatarId}/versions")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVersion(@PathVariable String avatarId,
                            @RequestBody ApiDtos.AvatarVersionRequest req) {
        avatarService.saveVersion(avatarId, req);
    }

    @GetMapping("/avatars/{avatarId}/versions")
    public List<ApiDtos.AvatarVersionResponse> listVersions(@PathVariable String avatarId) {
        return avatarService.listVersions(avatarId);
    }

    @DeleteMapping("/avatars/{avatarId}/versions/{versionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVersion(@PathVariable String avatarId,
                              @PathVariable String versionId) {
        avatarService.deleteVersion(avatarId, versionId);
    }

    @PatchMapping("/avatars/{avatarId}/versions/{versionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersionName(@PathVariable String avatarId,
                                  @PathVariable String versionId,
                                  @RequestBody ApiDtos.UpdateVersionNameRequest req) {
        avatarService.updateVersionName(avatarId, versionId, req.name());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiDtos.ErrorResponse> handleNotFound(NoSuchElementException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiDtos.ErrorResponse("NOT_FOUND", ex.getMessage(), req.getRequestURI(), Instant.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiDtos.ErrorResponse> handleBadRequest(IllegalArgumentException ex, HttpServletRequest req) {
        return ResponseEntity.badRequest()
            .body(new ApiDtos.ErrorResponse("BAD_REQUEST", ex.getMessage(), req.getRequestURI(), Instant.now()));
    }
}
