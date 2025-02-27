package cheongchul.cheongchul_eolam.dto.boarddto;

import cheongchul.cheongchul_eolam.domain.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
  private long boardId;
  private String title;
  private String content;
  private String category;
  private String authorName;
  private String authorDepartment;
  private String authorUniversity;
  private String formatDate;
  private String universityImgUrl;
  private boolean isBookmarked;

  public BoardResponseDTO(Board board, List<Long> bookmarkedBoardIds) {
    this.boardId = board.getBoardId();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.category = board.getCategory();
    this.authorName = board.getMember().getNickname();
    this.authorDepartment = board.getMember().getDepartment();
    this.authorUniversity = board.getMember().getUniversity();
    this.universityImgUrl = board.getMember().getUniversityImgUrl();
    this.formatDate = formatDate(board.getCreatedAt());
    this.isBookmarked = bookmarkedBoardIds.contains(board.getBoardId());
  }
  private String formatDate(LocalDateTime createdAt) {
    return createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
  }
}
