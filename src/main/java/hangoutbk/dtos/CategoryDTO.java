package hangoutbk.dtos;

import hangoutbk.entities.Event;
import lombok.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDTO {

    private String name;
}
