package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Board;
import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.UpdateMemberDTO;
import cheongchul.cheongchul_eolam.exception.CustomException;
import cheongchul.cheongchul_eolam.exception.ErrorCode;
import cheongchul.cheongchul_eolam.mapper.MemberMapper;
import cheongchul.cheongchul_eolam.repository.BoardRepository;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository, MemberMapper memberMapper,BoardRepository boardRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    //회원 조회
    public MemberResponseDTO findById (long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
        return memberMapper.toMemberResponseDTO(member);
    }

    //회원 정보 수정
    public MemberResponseDTO updatedMember(long memberId, UpdateMemberDTO updateMemberDTO) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->  new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));

        if (updateMemberDTO.getNickname() != null){
            member.setNickname(updateMemberDTO.getNickname());
        }
        if (updateMemberDTO.getUniversity() != null){
            member.setUniversity(updateMemberDTO.getUniversity());
        }
        if (updateMemberDTO.getDepartment() != null){
            member.setDepartment(updateMemberDTO.getDepartment());
        }
        if (updateMemberDTO.getRole() != null){
            member.setRole(updateMemberDTO.getRole());
        }
        return memberMapper.toMemberResponseDTO(member);
    }
    //회원 비밀번호 수정
    public MemberResponseDTO updatedPassword(long memberId,String oldPassword, String newPassword){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->  new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(oldPassword,member.getPassword())){
            throw new CustomException(ErrorCode.UNAUTHORIZED, "기존 비밀번호가 일치하지 않습니다.");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedNewPassword);
        return memberMapper.toMemberResponseDTO(member);
    }

    //회원 탈퇴
    public void deleteMember(long memberId) {
        Member deleteMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));
        memberRepository.delete(deleteMember);
    }
}