package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //글생성
    public Board createdBoard(Board board) {
        boardRepository.save(board);
        return board;
    }
    //글조회
    public Board findById(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new NoSuchElementException("존재하지 않는 게시글 입니다."));
        return board;
    }
    //게시판 전체 조회
    public List<Board> allBoards(){
        return boardRepository.findAll();
    }

    //글수정
    public Board updatedBoard(long boardId,Board updatedBoard){
        Board board = boardRepository.findById(boardId).orElseThrow(()->new NoSuchElementException("존재하지 않는 게시글 입니다."));

        if (updatedBoard.getTitle() != null) {
            board.setTitle(updatedBoard.getTitle());
        }
        if (updatedBoard.getCategory() != null) {
            board.setCategory(updatedBoard.getCategory());
        }
        if (updatedBoard.getContent() != null) {
            board.setContent(updatedBoard.getContent());
        }
        return boardRepository.save(board);
    }

    //글삭제
    public void deleteBoard(long boardId){
       boardRepository.deleteById(boardId);
    }
}
