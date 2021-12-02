package hangoutbk.dtos.builders;

import hangoutbk.dtos.PersonDTO;
import hangoutbk.entities.Person;
import lombok.Builder;

import java.util.UUID;

@Builder
public class PersonBuilder {


    public static PersonDTO toPersonDTO(Person person) {
        return new PersonDTO(person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPassword(),
                person.getRole(),
                person.getEvents());
    }

    public static Person toEntity(PersonDTO personDTO) {
        return new Person(UUID.randomUUID(),
                personDTO.getFirstName(),
                personDTO.getLastName(),
                personDTO.getEmail(),
                personDTO.getPassword(),
                personDTO.getRole(),
                personDTO.getEvents());
    }
}
