package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.MemberDTO;
import cheongchul.cheongchul_eolam.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 가입
    @PostMapping("/register")
    public ResponseEntity <MemberDTO> register(@RequestBody Member member) {
        try{
            MemberDTO newMember = memberService.register(member);
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    //회원 로그인
    @PostMapping("login")
    public ResponseEntity<MemberDTO> login(@RequestBody Map<String,String> loginRequest){
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");

           MemberDTO loginedMember =  memberService.login(email,password);
            return new ResponseEntity<>(loginedMember,HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }

    }
    //회원 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> findMemberById(@PathVariable long memberId){
        try{
            MemberDTO foundIdMember = memberService.findById(memberId);
            return new ResponseEntity<>(foundIdMember,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    //회원 수정
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable long memberId, @RequestBody Member updateMember) {
        try {
            MemberDTO updatedMember = memberService.updatedMember(memberId, updateMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{memberId}/password")
    public ResponseEntity<MemberDTO> updatePassword(@PathVariable long memberId, @RequestBody Map<String, String> passwordRequest) {
        try {
            String oldPassword = passwordRequest.get("oldPassword");
            String newPassword = passwordRequest.get("newPassword");

            MemberDTO updatedMember = memberService.updatedPassword(memberId, oldPassword, newPassword);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable long memberId){         try {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
