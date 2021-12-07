package hangoutbk.dtos;

import hangoutbk.entities.Category;
import hangoutbk.entities.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EventDTO {

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private float price;

    private String description;

    private boolean confirmationStatus;

    private String category;

    private Person person;
}
