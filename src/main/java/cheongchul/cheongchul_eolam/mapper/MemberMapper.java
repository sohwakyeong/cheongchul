package cheongchul.cheongchul_eolam.mapper;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "memberId", ignore = true)
    @Mapping(target = "admin", constant = "false")

    Member toMemberEntity(SignupRequestDTO signupRequestDTO);

    MemberResponseDTO toMemberResponseDTO(Member member);
}

