package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Board;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class BoardService {

    private final Map<Long,Board> boardStore = new HashMap<>();
    private long boardSequence = 0L;

    //글생성
    public Board createdBoard(Board board) {
        board.setBoardId(++boardSequence);
        boardStore.put(board.getBoardId(), board);
        return board;
    }
    //글조회
    public Board findById(long boardId) {
        Board board = boardStore.get(boardId);
        if(board == null) {
            throw new NoSuchElementException("존재하지 않는 게시글 입니다.");
        }
        return board;
    }
    //게시판 전체 조회
    public Map<Long,Board> allBoards(){
        return boardStore;
    }

    //글수정
    public Board updatedBoard(long boardId,Board updatedBoard){
        Board board = boardStore.get(boardId);
        if (board == null){
            throw new NoSuchElementException("존재하지 않는 게시글 입니다.");
        }
        if (updatedBoard.getTitle() != null) {
            board.setTitle(updatedBoard.getTitle());
        }
        if (updatedBoard.getCategory() != null) {
            board.setTitle(updatedBoard.getCategory());
        }
        if (updatedBoard.getContent() != null) {
            board.setTitle(updatedBoard.getContent());
        }
        return board;
    }

    //글삭제
    public void deleteBoard(long boardId){
       boardStore.remove(boardId);
    }
}
