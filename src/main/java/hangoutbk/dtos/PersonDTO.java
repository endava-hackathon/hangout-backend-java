package hangoutbk.dtos;

import hangoutbk.entities.Event;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    private List<Event> events;
}
