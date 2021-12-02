package hangoutbk.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginDTO {

    private String email;
    private String password;
}
