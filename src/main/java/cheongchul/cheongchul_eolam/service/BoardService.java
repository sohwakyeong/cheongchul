package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.boarddto.*;
import cheongchul.cheongchul_eolam.exception.CustomException;
import cheongchul.cheongchul_eolam.exception.ErrorCode;
import cheongchul.cheongchul_eolam.mapper.BoardMapper;
import cheongchul.cheongchul_eolam.repository.BoardRepository;
import cheongchul.cheongchul_eolam.repository.BookmarkRepository;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BookmarkService bookmarkService;
    private final BookmarkRepository bookmarkRepository;
    private final BoardMapper boardMapper;

    public BoardService(MemberRepository memberRepository, BoardRepository boardRepository, BookmarkService bookmarkService, BookmarkRepository bookmarkRepository, BoardMapper boardMapper) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.bookmarkService = bookmarkService;
        this.bookmarkRepository = bookmarkRepository;
        this.boardMapper = boardMapper;
    }

    //글생성
    public BoardResponseDTO createBoard(BoardCreateDTO boardCreateDTO, long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
        Board newBoard = boardMapper.toBoard(boardCreateDTO, member);
        boardRepository.save(newBoard);
        System.out.println("New Board ID: " + newBoard.getBoardId());
        return boardMapper.toBoardResponseDTO(newBoard);
    }

    //글조회
    public BoardResponseDTO findById(long boardId,long memberId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글 입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));

        BoardResponseDTO boardResponseDTO = boardMapper.toBoardResponseDTOWithId(board);
        boardResponseDTO.setBookmarked(bookmarkService.isBookmarked(board,member));

        return boardResponseDTO;
    }

    //게시판 전체 조회
    public PageResponseDTO allBoards(Pageable pageable,Long memberId,String category) {

        Page<Board> boardPage;

        if(!"all".equals(category) && category != null) {
            boardPage = boardRepository.findByCategory(category,pageable);
        } else {
            boardPage = boardRepository.findAll(pageable);
        }

        List<Long> bookmarkedBoardIds = (memberId != null) ? bookmarkRepository.findBookmarkedBoardMemberId(memberId): new ArrayList<>();

        List<BoardResponseDTO> boardResponseDTOs = boardPage.getContent().stream()
                .map(board -> new BoardResponseDTO(board,bookmarkedBoardIds))
                .collect(Collectors.toList());

        PageInfoDTO pageInfo = new PageInfoDTO(boardPage.getNumber(), boardPage.getSize(),
                boardPage.getTotalElements(), boardPage.getTotalPages());

        return new PageResponseDTO(boardResponseDTOs,pageInfo);
    }


    //글수정
    @Transactional
    public BoardResponseDTO updatedBoard(long boardId, long memberId,BoardUpdateDTO boardUpdateDTO) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글 입니다."));
        Member author = board.getMember();
        if(author == null || author.getMemberId() != memberId) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "해당글의 작성자만 수정이 가능합니다.");
        }
        if (!boardUpdateDTO.getTitle().isEmpty() && !board.getTitle().equals(boardUpdateDTO.getTitle())) {
            board.setTitle(boardUpdateDTO.getTitle());
        }

        if (!boardUpdateDTO.getCategory().isEmpty() && !board.getCategory().equals(boardUpdateDTO.getCategory())) {
            board.setCategory(boardUpdateDTO.getCategory());
        }

        if (!boardUpdateDTO.getContent().isEmpty() && !board.getContent().equals(boardUpdateDTO.getContent())) {
            board.setContent(boardUpdateDTO.getContent());
        }

        return boardMapper.toBoardResponseDTOWithId(board);
    }

    //글삭제
    public void deleteBoard(long boardId, long memberId) {
      Board board =  boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,"해당 게시글이 존재하지 않습니다."));
      Member author = board.getMember();
      if(author == null || author.getMemberId() != memberId) {
          throw new CustomException(ErrorCode.UNAUTHORIZED, "해당글의 작성자만 삭제가 가능합니다.");
      }
      boardRepository.deleteById(boardId);
    }

    public Long findAuthorIdByBoardId(long boardId,long memberId) {
        System.out.println("시작");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글 입니다."));
        long boardAuthorId = board.getMember().getMemberId();
        return boardAuthorId;
    }
}
// allBoards 무한 스크롤이 가능한 페이지네이션으로 바꾸기/ createBoard useReducer 상태 관리 적용가능하게 바꾸기