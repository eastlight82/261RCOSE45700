package unflatten.demo.avatar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import unflatten.demo.avatar.domain.AvatarEntity;
import unflatten.demo.avatar.domain.AvatarVersionEntity;

import java.util.List;
import java.util.Optional;

public interface AvatarVersionRepository extends JpaRepository<AvatarVersionEntity, Long> {
    List<AvatarVersionEntity> findByAvatarOrderByCreatedAtAsc(AvatarEntity avatar);
    Optional<AvatarVersionEntity> findByAvatarAndVersionKey(AvatarEntity avatar, String versionKey);
    void deleteByAvatar(AvatarEntity avatar);
}
