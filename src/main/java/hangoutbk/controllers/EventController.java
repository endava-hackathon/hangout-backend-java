package hangoutbk.controllers;

import hangoutbk.dtos.EventDTO;
import hangoutbk.services.EventService;
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

    @GetMapping("/getAllConfirmed")
    public ResponseEntity<List<EventDTO>> getEventsConfirmed() {
        List<EventDTO> dtos = eventService.findEventsConfirmed();
        LOGGER.info("searching for events");
        return new ResponseEntity<>(dtos, HttpStatus.FOUND);
    }

    @GetMapping("/getAllConfirmedByStartDate")
    public ResponseEntity<List<EventDTO>> getEventsConfirmedByStartDate() {
        List<EventDTO> dtos = eventService.findEventsConfirmedByStartDate();
        LOGGER.info("searching for events");
        return new ResponseEntity<>(dtos, HttpStatus.FOUND);
    }

    @GetMapping("/getAllConfirmedByEndDate")
    public ResponseEntity<List<EventDTO>> getEventsConfirmedByEndDate() {
        List<EventDTO> dtos = eventService.findEventsConfirmedByEndDate();
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

        eventService.update(eventDTO,name);

        return new ResponseEntity<String>("Evenet updated successfully", HttpStatus.OK);
    }

    //Confirm OR Reject Event as admin
    //status este boolean
    @RequestMapping(value = "/confirm/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> confirmEvent(@PathVariable("name") String name){
        EventDTO currentEvent = eventService.findEventByName(name);

        if (currentEvent == null){
            LOGGER.info("Event with name {} not found", name);
            return new ResponseEntity<String>("Event not found",HttpStatus.NOT_FOUND);
        }

        //se seteaza statusul la true de la false
      //  currentEvent.setConfirmationStatus(true);

        eventService.updateConfirmation(currentEvent);

        return new ResponseEntity<String>("Evenet confirmed successfully", HttpStatus.OK);
    }

    ///DELETE EVENT BY NAME

  //  @PostMapping("/delete")
    @RequestMapping(value = "/delete/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable("name") String name) {
        LOGGER.info("deleting event with name: {}", name);

        eventService.delete(name);

        return new ResponseEntity<String>("Event deleted successfully",HttpStatus.OK);
    }

    }
