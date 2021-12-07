package hangoutbk.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "confirmation_status", nullable = false)
    private boolean confirmationStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Person person;

    /*public Event(String name, LocalDateTime startDate, LocalDateTime endDate, float price, String description, Category category, Person person){
        this.name=name;
        this.startDate
    }*/

}
