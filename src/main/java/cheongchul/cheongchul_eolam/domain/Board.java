package cheongchul.cheongchul_eolam.domain;

import cheongchul.cheongchul_eolam.dto.MemberDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {
    private long boardId;
    private String title;
    private String content;
    private String category;
    private Member member;
}
