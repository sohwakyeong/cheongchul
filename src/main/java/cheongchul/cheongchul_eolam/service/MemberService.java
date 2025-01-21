//package cheongchul.cheongchul_eolam.service;
//
//import cheongchul.cheongchul_eolam.domain.Member;
//import cheongchul.cheongchul_eolam.dto.MemberDTO;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//// 역할은 선생 학생만 있고, 나머지 없음 : isAdmin boolean추가
//@Service
//public class MemberService {
//
//
//    private final PasswordEncoder passwordEncoder;
//    private final Map<Long, Member> memberStore = new HashMap<>();
//
//
//
//    private long sequence = 0L;
//
//    public MemberService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    //회원 가입
//    public MemberDTO register(Member member){
//       member.setMemberId(++sequence);
//
//       String encodedPassword = passwordEncoder.encode(member.getPassword());
//
//       memberStore.put(member.getMemberId(),member);
//       return convertToDTO(member);
//    }
//
//    //회원 로그인
//    public MemberDTO login(String email,String password){
//       for (Member member:memberStore.values()){
//           if (member.getEmail().equals(email)) {
//               if (passwordEncoder.matches(password, member.getPassword())){
//                   return convertToDTO(member);
//               }else {
//                   throw new IllegalArgumentException("비밀번호 입력 오류입니다.");
//               }
//           }
//       }
//       throw new IllegalArgumentException("이메일을 확인 해주세요.");
//    }
//
//
//    //회원 조회
//    public MemberDTO findById (long memberId){
//        Member member = memberStore.get(memberId);
//        if (member == null) {
//            throw new NoSuchElementException("존재하지 않는 회원입니다.");
//        }
//        return convertToDTO(member);
//    }
//
//    //회원 정보 수정
//    public MemberDTO updatedMember(long memberId,Member updatedMember) {
//        Member member = memberStore.get(memberId);
//        if(member == null)
//            throw new NoSuchElementException("존재하지 않는 회원입니다.");
//
//        if (updatedMember.getNickname() != null){
//            member.setNickname(updatedMember.getNickname());
//        }
//        if (updatedMember.getUniversity() != null){
//            member.setNickname(updatedMember.getUniversity());
//        }
//        if (updatedMember.getDepartment() != null){
//            member.setNickname(updatedMember.getDepartment());
//        }
//        if (updatedMember.getRole() != null){
//            member.setNickname(updatedMember.getRole());
//        }
//        return convertToDTO(member);
//    }
//    //회원 비밀번호 수정
//    public MemberDTO updatedPassword(long memberId,String oldPassword,String newPassword){
//        Member member = memberStore.get(memberId);
//        if(member == null){
//            throw new NoSuchElementException("존재하지 않는 회원입니다.");
//        }
//        if (!passwordEncoder.matches(oldPassword,member.getPassword())){
//            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
//        }
//        String encodedNewPassword = passwordEncoder.encode(newPassword);
//        member.setPassword(encodedNewPassword);
//        return convertToDTO(member);
//    }
//
//    //회원 탈퇴
//    public void deleteMember(long memberId){
//      memberStore.remove(memberId);
//    }
//
//    private MemberDTO convertToDTO(Member member) {
//        return new MemberDTO(
//                member.getMemberId(),
//                member.getEmail(),
//                member.getName(),
//                member.getNickname(),
//                member.getRole(),
//                member.getUniversity(),
//                member.getDepartment(),
//                member.isAdmin()
//        );
//    }
//}