package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.repository.BoardRepository;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        Member member = new Member("test12@example.com", "Test User");
        memberRepository.save(member);

        Board board = new Board();
        board.setTitle("Test Board");
        board.setContent("Test Content");
        board.setMember(member);
        boardRepository.save(board);
    }

    @AfterEach
    public void clearRepository() {
        memberRepository.deleteAll();
    }

    @Test
    void testCreateBoard() {
        Board board = new Board();
        board.setTitle("New Test Board");
        board.setContent("New Content");

        Board createdBoard = boardService.createdBoard(board);

        assertThat(createdBoard.getTitle()).isEqualTo("New Test Board");
        assertThat(createdBoard.getContent()).isEqualTo("New Content");
    }

    @Test
    void testFindById() {
        Board board = boardRepository.findAll().get(0);
        Board foundBoard = boardService.findById(board.getBoardId());

        assertThat(foundBoard.getTitle()).isEqualTo(board.getTitle());
        assertThat(foundBoard.getContent()).isEqualTo(board.getContent());
    }

    @Test
    void testUpdateBoard() {
        Board board = boardRepository.findAll().get(0);
        board.setTitle("Updated Title");
        board.setContent("Updated Content");

        Board updatedBoard = boardService.updatedBoard(board.getBoardId(), board);

        assertThat(updatedBoard.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedBoard.getContent()).isEqualTo("Updated Content");
    }

    @Test
    void testDeleteBoard() {
        Board board = boardRepository.findAll().get(0);
        boardService.deleteBoard(board.getBoardId());

        Optional<Board> deletedBoard = boardRepository.findById(board.getBoardId());
        assertThat(deletedBoard).isEmpty();
    }
}
