package hangoutbk.controllers;

import hangoutbk.dtos.CategoryDTO;
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
@RequestMapping(value = "/event")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EventDTO>> getEvents() {
        List<EventDTO> dtos = eventService.findEvents();
        LOGGER.info("searching for events");
        return new ResponseEntity<>(dtos, HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> insertEvent(@RequestBody EventDTO eventDTO) {
        LOGGER.info("inserting event: {}", eventDTO);
        UUID eventID = eventService.insert(eventDTO);
        return new ResponseEntity<>(eventID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllEventsByCategory")
    public ResponseEntity<List<EventDTO>> getAllEventsByCategoryOfUser(String category) {
        List<EventDTO> dto = eventService.findEventByCategory(category);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /// UPDATE

    @RequestMapping(value = "/update/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> update(@PathVariable("name") String name, @RequestBody EventDTO eventDTO){
        LOGGER.info("updating event: {}", eventDTO);
        EventDTO currentEvent = eventService.findEventByName(name);

        if (currentEvent == null){
            LOGGER.info("Event with name {} not found", name);
            return new ResponseEntity<String>("Event not found",HttpStatus.NOT_FOUND);
        }

        //updatare colane eveniment
        currentEvent.setName(eventDTO.getName());
        currentEvent.setCategory(eventDTO.getCategory());
        currentEvent.setDescription(eventDTO.getDescription());
        currentEvent.setPrice(eventDTO.getPrice());
        currentEvent.setStartDate(eventDTO.getStartDate());
        currentEvent.setEndDate(eventDTO.getEndDate());

        eventService.update(currentEvent);

        return new ResponseEntity<String>("Evenet updated successfully", HttpStatus.OK);
    }

    //Confirm OR Reject Event as admin
    //status este boolean
    @RequestMapping(value = "/confirm/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> update(@PathVariable("name") String name, boolean status){
        EventDTO currentEvent = eventService.findEventByName(name);

        if (currentEvent == null){
            LOGGER.info("Event with name {} not found", name);
            return new ResponseEntity<String>("Event not found",HttpStatus.NOT_FOUND);
        }

        //se seteaza statusul
        currentEvent.setConfirmationStatus(status);

        eventService.update(currentEvent);

        return new ResponseEntity<String>("Evenet confirmed/rejected successfully", HttpStatus.OK);
    }

}
