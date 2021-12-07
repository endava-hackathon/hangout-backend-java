package hangoutbk.dtos.builders;

import hangoutbk.dtos.EventDTO;
import hangoutbk.entities.Category;
import hangoutbk.entities.Event;
import hangoutbk.repositories.CategoryRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public class EventBuilder {

   // private static CategoryRepository categoryRepository;

    public static EventDTO toEventDTO(Event event) {
        return new EventDTO(event.getName(),
                event.getStartDate(),
                event.getEndDate(),
                event.getPrice(),
                event.getDescription(),
                event.isConfirmationStatus(),
                event.getCategory().getName(),
                event.getPerson());
    }

    public static Event toEntity(EventDTO eventDTO) {
        return new Event(UUID.randomUUID(),
                eventDTO.getName(),
                eventDTO.getStartDate(),
                eventDTO.getEndDate(),
                eventDTO.getPrice(),
                eventDTO.getDescription(),
                eventDTO.isConfirmationStatus(),
                null,
                eventDTO.getPerson());
    }
}
