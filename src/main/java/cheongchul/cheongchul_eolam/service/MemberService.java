package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.MemberDTO;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import jakarta.persistence.Id;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {


    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberService(PasswordEncoder passwordEncoder,MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public MemberDTO register(Member member){
       String encodedPassword = passwordEncoder.encode(member.getPassword());
       member.setPassword(encodedPassword);
       memberRepository.save(member);
       return convertToDTO(member);
    }

    //회원 로그인
    public MemberDTO login(String email,String password){
       Member member = memberRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("이메일을 확인 해주세요."));
       if(!passwordEncoder.matches(password, member.getPassword())){
           throw new IllegalArgumentException("비밀번호 입력 오류입니다.");
       }
        return convertToDTO(member);
    }


    //회원 조회
    public MemberDTO findById (long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new NoSuchElementException("존재하지 않는 회원입니다."));
        return convertToDTO(member);
    }

    //회원 정보 수정
    public MemberDTO updatedMember(long memberId,Member updatedMember) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 회원입니다."));

        if (updatedMember.getNickname() != null){
            member.setNickname(updatedMember.getNickname());
        }
        if (updatedMember.getUniversity() != null){
            member.setNickname(updatedMember.getUniversity());
        }
        if (updatedMember.getDepartment() != null){
            member.setNickname(updatedMember.getDepartment());
        }
        if (updatedMember.getRole() != null){
            member.setNickname(updatedMember.getRole());
        }
        return convertToDTO(member);
    }
    //회원 비밀번호 수정
    public MemberDTO updatedPassword(long memberId,String oldPassword,String newPassword){
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
        return convertToDTO(member);
    }

    //회원 탈퇴
    public void deleteMember(long memberId){
      memberRepository.deleteById(memberId);
    }

    private MemberDTO convertToDTO(Member member) {
        return new MemberDTO(
                member.getMemberId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getRole(),
                member.getUniversity(),
                member.getDepartment()
        );
    }
}