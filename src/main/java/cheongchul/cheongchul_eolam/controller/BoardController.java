package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.dto.boarddto.BoardCreateDTO;
import cheongchul.cheongchul_eolam.dto.boarddto.BoardResponseDTO;
import cheongchul.cheongchul_eolam.dto.boarddto.BoardUpdateDTO;
import cheongchul.cheongchul_eolam.dto.boarddto.PageResponseDTO;
import cheongchul.cheongchul_eolam.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cheongchul.cheongchul_eolam.security.authUtils.getMemberId;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //포스트 글
    @PostMapping("/create")
    public ResponseEntity<BoardResponseDTO> createdBoard(@RequestBody BoardCreateDTO boardCreateDTO, Authentication authentication) {

        long memberId = getMemberId(authentication);
        BoardResponseDTO createdBoard = boardService.createBoard(boardCreateDTO, memberId);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    //겟 개별 글
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getEachBoard(@PathVariable("boardId") long boardId,Authentication authentication){
            System.out.println("요청 들어옴: boardId = " + boardId);
            long memberId = getMemberId(authentication);
            BoardResponseDTO getBoard = boardService.findById(boardId,memberId);
            return new ResponseEntity<>(getBoard,HttpStatus.OK);
    }
    //겟 전체글
    @GetMapping
    public ResponseEntity<PageResponseDTO> getAllBoards(Authentication authentication, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "3") int size,@RequestParam(value = "category", required = false) String category) {
        Long memberId = (authentication !=null) ? getMemberId(authentication):null;
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("CreatedAt").descending());
        PageResponseDTO response = boardService.allBoards(pageRequest, memberId, category);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //패치 글
    @PatchMapping("/{boardId}")
    public ResponseEntity<Board> updateBoard (long boardId, BoardUpdateDTO boardUpdateDTO) {
        try {
            Board board = boardService.updatedBoard(boardId, boardUpdateDTO);
            return new ResponseEntity<>(board, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // 글삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long boardId,Authentication authentication) {
            long memberId = getMemberId(authentication);
            boardService.deleteBoard(boardId,memberId);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
