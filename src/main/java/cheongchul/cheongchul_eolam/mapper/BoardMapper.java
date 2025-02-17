package cheongchul.cheongchul_eolam.mapper;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.boarddto.BoardCreateDTO;
import cheongchul.cheongchul_eolam.dto.boarddto.BoardResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    @Mapping(target = "boardId",ignore = true)
    @Mapping(target = "formatDate",expression = "java(formatDate(board.getCreatedAt()))")
    BoardResponseDTO toBoardResponseDTO(Board board);

    default String formatDate(java.time.LocalDateTime createdAt) {
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return createdAt.format(formatter);
        }
        return null;
    }
    @Mapping(target = "boardId", ignore=true)
    @Mapping(target = "member", source = "member")
    @Mapping(target = "authorName", expression = "java(member.getNickname())")
    @Mapping(target = "authorDepartment", expression = "java(member.getDepartment())")
    @Mapping(target = "authorUniversity", expression = "java(member.getUniversity())")
    @Mapping(target = "title", source = "boardCreateDTO.title")
    @Mapping(target = "content", source = "boardCreateDTO.content")
    @Mapping(target = "category", source = "boardCreateDTO.category")
    Board toBoard(BoardCreateDTO boardCreateDTO, Member member);
}
