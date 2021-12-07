package hangoutbk.controllers;

import hangoutbk.dtos.EventDTO;
import hangoutbk.dtos.PersonDTO;
import hangoutbk.entities.Event;
import hangoutbk.services.EventService;
import hangoutbk.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping(value = "/person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;
    private final EventService eventService;

    @Autowired
    public PersonController(PersonService personService, EventService eventService) {
        this.personService = personService;
        this.eventService = eventService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        LOGGER.info("searching for persons");
        return new ResponseEntity<>(dtos, HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> insertPerson(@RequestBody PersonDTO personDTO) {
        LOGGER.info("inserting person: {}", personDTO);
        personDTO.setRole("user");
        UUID personID = personService.insert(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getClient/{email}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable("email") String email) {
        PersonDTO dto = personService.findPersonByEmail(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

    @RequestMapping(value = "/update/{email}", method = RequestMethod.POST)
    public ResponseEntity<String> update(@PathVariable("email") String email, @RequestBody PersonDTO personDTO){
        LOGGER.info("updating person: {}", personDTO);
        PersonDTO currentPerson = personService.findPersonByEmail(email);

        if (currentPerson == null){
            LOGGER.info("Person with email {} not found", email);
            return new ResponseEntity<String>("Person not found",HttpStatus.NOT_FOUND);
        }

        currentPerson.setFirstName(personDTO.getFirstName());
        currentPerson.setLastName(personDTO.getLastName());
        currentPerson.setEmail(personDTO.getEmail());
        currentPerson.setPassword(personDTO.getPassword());

        personService.update(currentPerson);

        return new ResponseEntity<String>("Person updated successfully", HttpStatus.OK);
    }

    //add event to person
    @RequestMapping(value = "/addEvent/{email}/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> addEvent(@PathVariable("email") String email,@PathVariable("name") String name){

        personService.addEvent(email, name);

        return new ResponseEntity<String>("Person added new event", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody String email){
        LOGGER.info("deleting person with email: {}", email);
        PersonDTO person = personService.findPersonByEmail(email);


        if (person == null){
            LOGGER.info("Unable to delete. Person with email {} not found", email);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personService.delete(person);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAllEventsOfUser/{email}")
    public ResponseEntity<List<EventDTO>> getAllEventsOfUser(@PathVariable("email") String email) {
        List<EventDTO> dto = personService.findAllEventsOfUser(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
