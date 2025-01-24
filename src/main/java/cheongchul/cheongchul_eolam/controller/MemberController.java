package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.domain.Member;
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
 * - [2025-01-24] @ControllerAdvice 사용 해서 에러 처리 분리 시키기.
 * - [2025-01-24] MemberRequest DTO/MemberResponse DTO 따로 만들어서  엔티티와 역할분리 및 보안강화.
 * - [2025-01-24] 회원 인가처리를 위해 security 도입
 * - [2025-01-25] 보드 DTO작성
 */


@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 가입
    @PostMapping("/register")
    public ResponseEntity <MemberResponseDTO> register(@RequestBody SignupRequestDTO signupRequestDTO) {
        try{
            MemberResponseDTO newMember = memberService.register(signupRequestDTO);
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    //회원 로그인
    @PostMapping("login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try {
            String email = loginRequestDTO.getEmail();
            String password = loginRequestDTO.getPassword();

           MemberResponseDTO loginedMember =  memberService.login(email,password);
            return new ResponseEntity<>(loginedMember,HttpStatus.OK);
        }
        catch (Exception e) {
          return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    //회원 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> findMemberById(@PathVariable long memberId){
        try{
            MemberResponseDTO foundIdMember = memberService.findById(memberId);
            return new ResponseEntity<>(foundIdMember,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //회원 수정
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable long memberId, @RequestBody UpdateMemberDTO updateMemberDTO) {
        try{
           MemberResponseDTO updateMember =  memberService.updatedMember(memberId, updateMemberDTO);
            return new ResponseEntity<>(updateMember, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PatchMapping("/{memberId}/password")
    public ResponseEntity<MemberResponseDTO> updatePassword(@PathVariable long memberId, @RequestBody Map<String, String> passwordRequest) {
        try {
            String oldPassword = passwordRequest.get("oldPassword");
            String newPassword = passwordRequest.get("newPassword");

            MemberResponseDTO updatedMember = memberService.updatedPassword(memberId, oldPassword, newPassword);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable long memberId){
        try {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }
}
