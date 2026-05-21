package unflatten.demo.avatar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import unflatten.demo.avatar.domain.AvatarEntity;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<AvatarEntity, Long> {
    Optional<AvatarEntity> findByAvatarId(String avatarId);
    void deleteByAvatarId(String avatarId);
}
