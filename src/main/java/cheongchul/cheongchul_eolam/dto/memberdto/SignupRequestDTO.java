package cheongchul.cheongchul_eolam.dto.memberdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String role;
    private String university;
    private String department;
}
