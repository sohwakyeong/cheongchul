package cheongchul.cheongchul_eolam.dto.memberdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {
   private String email;
   private String password;
}
