package unflatten.demo.avatar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import unflatten.demo.avatar.domain.TemplateEntity;

import java.util.List;

public interface TemplateRepository extends JpaRepository<TemplateEntity, String> {
    List<TemplateEntity> findByActiveTrueOrderByNameAsc();
}
