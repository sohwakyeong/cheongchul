package cheongchul.cheongchul_eolam.repository;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.domain.Bookmark;
import cheongchul.cheongchul_eolam.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    boolean existsByBoardAndMember(Board board,Member member);
    Optional<Bookmark> findByBoardAndMember(Board board, Member member);

    @Query("SELECT b.board.id FROM Bookmark b WHERE b.member.id = :memberId")
    List<Long> findBookmarkedBoardMemberId(@Param("memberId")Long memberId);

    @Query("SELECT b.board FROM Bookmark b WHERE b.member.id = :memberId")
    List <Board> findBookmarkBoardsByMemberId(@Param("memberId")Long memberId);
}
