package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //포스트 글
    @PostMapping("/create")
    public ResponseEntity<Board> createdBoard(@RequestBody Board board){
        try {
            Board createdBoard  =  boardService.createdBoard(board);
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    //겟 개별 글
    @GetMapping("/{boardId}")
    public ResponseEntity<Board> getEachBoard(@PathVariable long boardId){
        try {
            Board getBoard = boardService.findById(boardId);
            return new ResponseEntity<>(getBoard,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    //겟 전체글
    @GetMapping("/all")
    public ResponseEntity<Map<Long,Board>> allBoard(){
      Map<Long,Board> boards = boardService.allBoards();
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }
    //패치 글
    @PatchMapping("/{boardId}")
    public ResponseEntity<Board> updateBoard (long boardId,Board updetedboard) {
        try {
            Board board = boardService.updatedBoard(boardId, updetedboard);
            return new ResponseEntity<>(board, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // 글삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long boardId) {
        try {
            boardService.deleteBoard(boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
