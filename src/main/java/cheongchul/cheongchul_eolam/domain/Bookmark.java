package cheongchul.cheongchul_eolam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private int bookmarkCount = 0;

    public void increaseBookmark() {
        this.bookmarkCount++;
    }
    public void decreaseBookmarkCount() {
        if(this.bookmarkCount > 0){
            this.bookmarkCount--;
        }
    }

    public Bookmark(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}
