package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.repository.BookmarkRepository;
import cheongchul.cheongchul_eolam.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static cheongchul.cheongchul_eolam.security.authUtils.getMemberId;
@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }
    @PostMapping("/{boardId}")
    public ResponseEntity<String> addBookmark(@PathVariable("boardId") Long boardId, Authentication authentication) {
        long memberId = getMemberId(authentication);
        bookmarkService.addBookmark(boardId,memberId);
        return new ResponseEntity<>("북마크가 추가되었습니다.", HttpStatus.CREATED);
    }
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> removeBookmark(@PathVariable("boardId") Long boardId,Authentication authentication) {
        long memberId = getMemberId(authentication);
        bookmarkService.removeBookmark(boardId,memberId);
        return new ResponseEntity<>("북마크가 삭제되었습니다",HttpStatus.OK);
    }
}
