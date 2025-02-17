package cheongchul.cheongchul_eolam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String role;
    private String university;
    private String department;
    private boolean isAdmin;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Board> boards;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
