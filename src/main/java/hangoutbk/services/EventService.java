package hangoutbk.services;

import hangoutbk.controllers.handlers.exceptions.model.ResourceNotFoundException;
import hangoutbk.dtos.EventDTO;
import hangoutbk.dtos.builders.EventBuilder;;
import hangoutbk.entities.Category;
import hangoutbk.entities.Event;
import hangoutbk.repositories.CategoryRepository;
import hangoutbk.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public EventService(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<EventDTO> findEvents() {
        List<Event> eventList = eventRepository.findAll();

        List<EventDTO> eventDTOS =new ArrayList<>();
        eventList.forEach( u->{
            eventDTOS.add(EventBuilder.toEventDTO(u));
        });

        return eventDTOS;
    }

    public List<EventDTO> findEventsConfirmed() {
        List<Event> eventList = eventRepository.findAll();

        List<Event> eventsConfirmed = new ArrayList<>();

        for(Event e: eventList){
            if(e.isConfirmationStatus()){
                eventsConfirmed.add(e);
            }
        }

        List<EventDTO> eventDTOS =new ArrayList<>();
        eventsConfirmed.forEach( u->{
            eventDTOS.add(EventBuilder.toEventDTO(u));
        });

        return eventDTOS;
    }

    public List<EventDTO> findEventsConfirmedByStartDate() {
        List<Event> eventList = eventRepository.findAll();

        List<Event> eventsConfirmed = new ArrayList<>();

        for(Event e: eventList){
            if(e.isConfirmationStatus()){
                eventsConfirmed.add(e);
            }
        }

        List<EventDTO> eventDTOS =new ArrayList<>();
        eventsConfirmed.forEach( u->{
            eventDTOS.add(EventBuilder.toEventDTO(u));
        });

        Comparator<EventDTO> mapComparator = Comparator.comparing(EventDTO::getStartDate);
        eventDTOS.sort(mapComparator);

        return eventDTOS;
    }

    public List<EventDTO> findEventsConfirmedByEndDate() {
        List<Event> eventList = eventRepository.findAll();

        List<Event> eventsConfirmed = new ArrayList<>();

        for(Event e: eventList){
            if(e.isConfirmationStatus()){
                eventsConfirmed.add(e);
            }
        }

        List<EventDTO> eventDTOS =new ArrayList<>();
        eventsConfirmed.forEach( u->{
            eventDTOS.add(EventBuilder.toEventDTO(u));
        });

        Comparator<EventDTO> mapComparator = Comparator.comparing(EventDTO::getEndDate);
        eventDTOS.sort(mapComparator);

        return eventDTOS;
    }

    public UUID insert(EventDTO eventDTO) {
        Category category=categoryRepository.findByName(eventDTO.getCategory());
        Event event = EventBuilder.toEntity(eventDTO);
        event.setConfirmationStatus(false);
        event.setCategory(category);
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
        if(event.isConfirmationStatus())
             return EventBuilder.toEventDTO(event);
        else
            throw new ResourceNotFoundException(Event.class.getSimpleName() + " with id: " +  event.getId());
    }

    public List<EventDTO> findEventByCategory(String eventCategory) {
        List<Event> eventList = eventRepository.findAll();

        List<Event> eventsByCategory = new ArrayList<>();

        for(Event e: eventList){
            if((e.getCategory().getName()).equals(eventCategory) && e.isConfirmationStatus()){
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

    public UUID update(EventDTO eventDTO,String name) {
        Category category=categoryRepository.findByName(eventDTO.getCategory());
        Event event = eventRepository.findEventByName(name);
        event.setCategory(category);

        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setPrice(eventDTO.getPrice());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());

        event = eventRepository.save(event);
        LOGGER.debug("Event with id {} was updated in db", event.getId());
        return event.getId();
    }

    public UUID updateConfirmation(EventDTO eventDTO) {
        Category category=categoryRepository.findByName(eventDTO.getCategory());
        Event event = eventRepository.findEventByName(eventDTO.getName());
        event.setCategory(category);
        event.setConfirmationStatus(true);
        event = eventRepository.save(event);
        LOGGER.debug("Event with id {} was updated in db", event.getId());
        return event.getId();
    }

    public void delete(String eventName) {
        Event event = eventRepository.findEventByName(eventName);
        eventRepository.deleteById(event.getId());
        LOGGER.debug("Event with id {} was deleted in db", event.getId());
    }
}
