package hangoutbk.repositories;

import hangoutbk.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, UUID> {

    Event findEventByName(String name);
}
