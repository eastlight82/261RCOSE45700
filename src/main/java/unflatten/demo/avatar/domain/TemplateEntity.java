package unflatten.demo.avatar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "templates")
public class TemplateEntity {

    @Id
    @Column(nullable = false, length = 120)
    private String id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String vrmUrl;

    @Column(columnDefinition = "TEXT")
    private String defaultValuesJson;

    @Column(columnDefinition = "TEXT")
    private String tagsJson;

    @Column(nullable = false)
    private boolean active = true;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public String getVrmUrl() { return vrmUrl; }
    public void setVrmUrl(String vrmUrl) { this.vrmUrl = vrmUrl; }
    public String getDefaultValuesJson() { return defaultValuesJson; }
    public void setDefaultValuesJson(String defaultValuesJson) { this.defaultValuesJson = defaultValuesJson; }
    public String getTagsJson() { return tagsJson; }
    public void setTagsJson(String tagsJson) { this.tagsJson = tagsJson; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
