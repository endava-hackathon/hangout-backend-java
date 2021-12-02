package hangoutbk.dtos.builders;

import hangoutbk.dtos.EventDTO;
import hangoutbk.entities.Event;
import lombok.Builder;

import java.util.UUID;

@Builder
public class EventBuilder {

    public static EventDTO toEventDTO(Event event) {
        return new EventDTO(event.getName(),
                event.getStartDate(),
                event.getEndDate(),
                event.getPrice(),
                event.getDescription(),
                event.isConfirmationStatus(),
                event.getCategory(),
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
                eventDTO.getCategory(),
                eventDTO.getPerson());
    }
}
