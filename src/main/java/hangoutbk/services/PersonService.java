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
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final EventRepository eventRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, EventRepository eventRepository) {
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
    }

    public List<PersonDTO> findPersons() {
        List<Person> personList = personRepository.findAll();

        List<PersonDTO> personDTOS=new ArrayList<>();
        personList.forEach( u->{
            personDTOS.add(PersonBuilder.toPersonDTO(u));
        });

        return personDTOS;
    }

    public PersonDTO findPersonByEmail(String username) {
        Person personOptional = personRepository.findByEmail(username);
        if (personOptional == null) {
            LOGGER.error("Person with email {} was not found in db", personOptional.getEmail());
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " +  personOptional.getId());
        }
        return PersonBuilder.toPersonDTO(personOptional);
    }

    public UUID insert(PersonDTO personDTO) {
        Person person = PersonBuilder.toEntity(personDTO);
        person = personRepository.save(person);
        LOGGER.debug("Person with id {} was inserted in db", person.getId());
        return person.getId();
    }

    public UUID update(PersonDTO personDTO) {
        Person person = PersonBuilder.toEntity(personDTO);
        person = personRepository.save(person);
        LOGGER.debug("Person with id {} was updated in db", person.getId());
        return person.getId();
    }

    public void delete(PersonDTO personDTO) {
        Person person = PersonBuilder.toEntity(personDTO);
        personRepository.deleteById(person.getId());
        LOGGER.debug("Person with id {} was deleted in db", person.getId());
    }

    public void addEvent(String email, String eventName) {
        Person person = personRepository.findByEmail(email);
        Event event = eventRepository.findEventByName(eventName);

        if(!person.getEvents().isEmpty()){
            List<Event> eventList = person.getEvents();
            eventList.addAll(person.getEvents());
            eventList.add(event);
            person.setEvents(eventList);
        }else{
            List<Event> eventList = new ArrayList<>();
            eventList.add(event);
            person.setEvents(eventList);
        }
        personRepository.save(person);
        LOGGER.debug("Person with id {} was deleted in db", person.getId());
    }

    public List<EventDTO> findAllEventsOfUser(String email){
        Person personOptional = personRepository.findByEmail(email);
        List<Event> events= personOptional.getEvents();

        return events.stream()
                .map(EventBuilder::toEventDTO)
                .collect(Collectors.toList());
    }
}
