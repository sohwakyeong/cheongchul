package cheongchul.cheongchul_eolam.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;
    private String title;
    private String content;
    private String category;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
