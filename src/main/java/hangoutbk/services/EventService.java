package hangoutbk.services;

import hangoutbk.controllers.handlers.exceptions.model.ResourceNotFoundException;
import hangoutbk.dtos.EventDTO;
import hangoutbk.dtos.PersonDTO;
import hangoutbk.dtos.builders.EventBuilder;
import hangoutbk.dtos.builders.PersonBuilder;
import hangoutbk.entities.Event;
import hangoutbk.entities.Person;
import hangoutbk.repositories.EventRepository;
import hangoutbk.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventDTO> findEvents() {
        List<Event> eventList = eventRepository.findAll();

        List<EventDTO> eventDTOS =new ArrayList<>();
        eventList.forEach( u->{
            eventDTOS.add(EventBuilder.toEventDTO(u));
        });

        return eventDTOS;
    }

    public UUID insert(EventDTO eventDTO) {
        Event event = EventBuilder.toEntity(eventDTO);
        event = eventRepository.save(event);
        LOGGER.debug("Event with id {} was inserted in db", event.getId());
        return event.getId();
    }

    public EventDTO findEventByName(String eventName) {
       Event event = eventRepository.findEventByName(eventName);

        if (event == null) {
            LOGGER.error("Event with name {} was not found in db", event.getName());
            throw new ResourceNotFoundException(Event.class.getSimpleName() + " with id: " +  event.getId());
        }
        return EventBuilder.toEventDTO(event);
    }

    public List<EventDTO> findEventByCategory(String eventCategory) {
        List<Event> eventList = eventRepository.findAll();

        List<Event> eventsByCategory = new ArrayList<>();

        for(Event e: eventList){
            if((e.getCategory().getName()).equals(eventCategory)){
                eventsByCategory.add(e);
            }
        }

        if (eventsByCategory.isEmpty()) {
            LOGGER.error("Events with category {} was not found in db", eventCategory);
            throw new ResourceNotFoundException(Event.class.getSimpleName() + " with category: " +  eventCategory);
        }

        List<EventDTO> eventDTOS =new ArrayList<>();
        eventsByCategory.forEach( u->{
            eventDTOS.add(EventBuilder.toEventDTO(u));
        });

        return eventDTOS;
    }

    public UUID update(EventDTO eventDTO) {
        Event event = EventBuilder.toEntity(eventDTO);
        event = eventRepository.save(event);
        LOGGER.debug("Event with id {} was updated in db", event.getId());
        return event.getId();
    }
}
