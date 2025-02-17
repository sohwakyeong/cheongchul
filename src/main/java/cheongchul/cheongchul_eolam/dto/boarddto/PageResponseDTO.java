package cheongchul.cheongchul_eolam.dto.boarddto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDTO {
    private List<BoardResponseDTO> data;
    private PageInfoDTO
            pageInfo;
}
