package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.dto.memberdto.LoginRequestDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import cheongchul.cheongchul_eolam.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //회원 가입
    @PostMapping("/register")
    public ResponseEntity<MemberResponseDTO> register(@RequestBody SignupRequestDTO signupRequestDTO) {
        MemberResponseDTO newMember = authService.register(signupRequestDTO);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    //회원 로그인
    @PostMapping("/login")
        public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
            String token = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
        }
}
