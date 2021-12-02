package hangoutbk.controllers;

import hangoutbk.dtos.LoginDTO;
import hangoutbk.dtos.LoginResponseDTO;
import hangoutbk.dtos.PersonDTO;
import hangoutbk.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private final PersonService personService;

    @Autowired
    public LoginController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping()
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        PersonDTO personDTO = personService.findPersonByEmail(loginDTO.getEmail());
        if(personDTO.getPassword().equals(loginDTO.getPassword())){
            loginResponseDTO.setStatus("ok");
            loginResponseDTO.setRole(personDTO.getRole());
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.FOUND);
        }else {
            loginResponseDTO.setStatus("not_ok");
            loginResponseDTO.setRole("");
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.NOT_FOUND);
        }
    }
}
