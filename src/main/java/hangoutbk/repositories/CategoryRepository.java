package hangoutbk.repositories;

import hangoutbk.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
