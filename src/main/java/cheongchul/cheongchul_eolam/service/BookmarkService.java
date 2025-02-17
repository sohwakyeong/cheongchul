package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.domain.Bookmark;
import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.exception.CustomException;
import cheongchul.cheongchul_eolam.exception.ErrorCode;
import cheongchul.cheongchul_eolam.repository.BoardRepository;
import cheongchul.cheongchul_eolam.repository.BookmarkRepository;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    public boolean isBookmarked(Board board ,Member member) {
        return bookmarkRepository.existsByBoardAndMember(board, member);
    }


    public void addBookmark(Long boardId,long memberId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND,"해당글을 찾을수가 없습니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND,"해당 회원을 찾을 수 없습니다."));

        Bookmark bookmark = new Bookmark(board,member);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmark (Long boardId,long memberId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND,"해당글을 찾을수가 없습니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND,"해당 회원을 찾을 수 없습니다."));

        Bookmark bookmark = bookmarkRepository.findByBoardAndMember(board, member).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND,"북마크가 존재하지 않습니다."));
        bookmarkRepository.delete(bookmark);
    }
}
