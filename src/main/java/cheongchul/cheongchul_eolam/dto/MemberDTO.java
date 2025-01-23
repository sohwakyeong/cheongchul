package cheongchul.cheongchul_eolam.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private long memberId;
    private String email;
    private String name;
    private String nickname;
    private String role;
    private String university;
    private String department;
}
