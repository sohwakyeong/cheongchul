package cheongchul.cheongchul_eolam.dto.boarddto;

import lombok.Getter;

@Getter
public class PageInfoDTO {
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PageInfoDTO(int page, int size, long totalElements, int totalPages) {
        this.page = page + 1;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
