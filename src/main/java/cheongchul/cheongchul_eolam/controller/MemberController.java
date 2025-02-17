package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.UpdateMemberDTO;
import cheongchul.cheongchul_eolam.security.CustomUserDetails;
import cheongchul.cheongchul_eolam.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import static cheongchul.cheongchul_eolam.security.authUtils.getMemberId;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 조회
    @GetMapping("/info")
    public ResponseEntity<MemberResponseDTO> findMemberById(Authentication authentication) {
        long memberId = getMemberId(authentication);
        MemberResponseDTO foundMember = memberService.findById(memberId);
        return new ResponseEntity<>(foundMember, HttpStatus.OK);
    }

    //회원 수정
    @PatchMapping("")
    public ResponseEntity<MemberResponseDTO> updateMember(Authentication authentication, @RequestBody UpdateMemberDTO updateMemberDTO) {
        long memberId = getMemberId(authentication);
        MemberResponseDTO updatedMember = memberService.updatedMember(memberId, updateMemberDTO);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<MemberResponseDTO> updatePassword(Authentication authentication, @RequestBody Map<String, String> passwordRequest) {
        String oldPassword = passwordRequest.get("oldPassword");
        String newPassword = passwordRequest.get("newPassword");

        long memberId = getMemberId(authentication);

        MemberResponseDTO updatedMember = memberService.updatedPassword(memberId, oldPassword, newPassword);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    //회원 탈퇴
    @DeleteMapping("")
    public ResponseEntity<Void> deleteMember(Authentication authentication) {
        long memberId = getMemberId(authentication);
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}