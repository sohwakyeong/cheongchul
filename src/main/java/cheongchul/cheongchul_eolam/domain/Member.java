package cheongchul.cheongchul_eolam.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Member  {

    private long memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String role;
    private String university;
    private String department;
    private boolean isAdmin = false;

    public Member(String email,String name){
        this.email = email;
        this.name = name;
    }
    public Member(long memberId,String email,List<String> role){
        this.memberId = memberId;
        this.email = email;
        this.role = this.role;
    }
}
