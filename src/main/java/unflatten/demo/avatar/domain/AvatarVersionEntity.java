package unflatten.demo.avatar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

@Entity
@Table(name = "avatar_versions", uniqueConstraints = {
    @UniqueConstraint(name = "uk_avatar_versions_avatar_key", columnNames = {"avatar_id", "versionKey"})
})
public class AvatarVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avatar_id", nullable = false)
    private AvatarEntity avatar;

    @Column(nullable = false, length = 120)
    private String versionKey;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String parametersJson;

    @Column(columnDefinition = "TEXT")
    private String thumbnailDataUrl;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public AvatarEntity getAvatar() { return avatar; }
    public void setAvatar(AvatarEntity avatar) { this.avatar = avatar; }
    public String getVersionKey() { return versionKey; }
    public void setVersionKey(String versionKey) { this.versionKey = versionKey; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParametersJson() { return parametersJson; }
    public void setParametersJson(String parametersJson) { this.parametersJson = parametersJson; }
    public String getThumbnailDataUrl() { return thumbnailDataUrl; }
    public void setThumbnailDataUrl(String thumbnailDataUrl) { this.thumbnailDataUrl = thumbnailDataUrl; }
    public Instant getCreatedAt() { return createdAt; }
}
