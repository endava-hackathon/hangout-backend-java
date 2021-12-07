package hangoutbk.entities;

import hangoutbk.entities.Event;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "person")
public class Person{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<Event> events;
}
