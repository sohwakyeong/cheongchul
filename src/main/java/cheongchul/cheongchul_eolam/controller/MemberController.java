package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.dto.memberdto.LoginRequestDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.UpdateMemberDTO;
import cheongchul.cheongchul_eolam.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * To-Do List:
 * - [2025-01-25] @ControllerAdvice 사용 해서 에러 처리 분리 시키기.
 * - [2025-01-27] 회원 인가처리를 위해 security 도입
 * - [2025-01-27] 보드 DTO작성
 * - pathvariable로 아이디 받는게아니라 , 다른방법 생각해보기.
 */

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }


    //회원 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> findMemberById(@PathVariable long memberId){
        MemberResponseDTO foundIdMember = memberService.findById(memberId);
        return new ResponseEntity<>(foundIdMember, HttpStatus.OK);
    }

    //회원 수정
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable long memberId, @RequestBody UpdateMemberDTO updateMemberDTO) {
        MemberResponseDTO updatedMember = memberService.updatedMember(memberId, updateMemberDTO);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @PatchMapping("/{memberId}/password")
    public ResponseEntity<MemberResponseDTO> updatePassword(@PathVariable long memberId, @RequestBody Map<String, String> passwordRequest) {
        String oldPassword = passwordRequest.get("oldPassword");
        String newPassword = passwordRequest.get("newPassword");

        MemberResponseDTO updatedMember = memberService.updatedPassword(memberId, oldPassword, newPassword);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    //회원 탈퇴
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}