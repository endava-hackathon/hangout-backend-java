package hangoutbk.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginResponseDTO {

    private String status;

    private String role;
}
