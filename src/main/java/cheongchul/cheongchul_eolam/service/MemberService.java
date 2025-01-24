package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.UpdateMemberDTO;
import cheongchul.cheongchul_eolam.mapper.MemberMapper;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository, MemberMapper memberMapper) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    //회원 가입
    public MemberResponseDTO register(SignupRequestDTO signupRequestDTO){
       Member member = memberMapper.toMemberEntity(signupRequestDTO);
       member.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
       memberRepository.save(member);
       return memberMapper.toMemberResponseDTO(member);
    }

    //회원 로그인
    public MemberResponseDTO login(String email, String password){
       Member member = memberRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("이메일을 확인 해주세요."));
       if(!passwordEncoder.matches(password, member.getPassword())){
           throw new IllegalArgumentException("비밀번호 입력 오류입니다.");
       }
        return memberMapper.toMemberResponseDTO(member);
    }


    //회원 조회
    public MemberResponseDTO findById (long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new NoSuchElementException("존재하지 않는 회원입니다."));
        return memberMapper.toMemberResponseDTO(member);
    }

    //회원 정보 수정
    public MemberResponseDTO updatedMember(long memberId, UpdateMemberDTO updateMemberDTO) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 회원입니다."));

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
    public MemberResponseDTO updatedPassword(long memberId, String oldPassword, String newPassword){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 회원입니다."));
        if(member == null){
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        if (!passwordEncoder.matches(oldPassword,member.getPassword())){
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedNewPassword);
        return memberMapper.toMemberResponseDTO(member);
    }

    //회원 탈퇴
    public void deleteMember(long memberId){
      memberRepository.deleteById(memberId);
    }
}