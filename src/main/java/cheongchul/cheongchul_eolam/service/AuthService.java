package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import cheongchul.cheongchul_eolam.exception.CustomException;
import cheongchul.cheongchul_eolam.exception.ErrorCode;
import cheongchul.cheongchul_eolam.mapper.MemberMapper;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import cheongchul.cheongchul_eolam.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberMapper = memberMapper;
    }

    //회원 가입
    public MemberResponseDTO register(SignupRequestDTO signupRequestDTO){
        validateEmail(signupRequestDTO.getEmail());
        Member member = memberMapper.toMemberEntity(signupRequestDTO);
        member.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        memberRepository.save(member);
        return memberMapper.toMemberResponseDTO(member);
    }

    //회원 로그인
    public String login(String email, String password){
          System.out.println("email:" + email);
          Member member = memberRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND,"이메일을 확인 해주세요."));
          System.out.println("member = " + member);
        
        long memberId = member.getMemberId();
        System.out.println("memberId = " + memberId);
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new CustomException(ErrorCode.UNAUTHORIZED, "비밀번호 입력 오류입니다.");
        }

        return jwtTokenProvider.createToken(memberId);
    }


    private void validateEmail(String email) {
        if(!isValidEmail(email)) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "이메일 형식이 올바르지 않습니다.");
        }
        if(memberRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.CONFLICT,"이미 사용 중인 이메일 입니다.");
        }
    }
    private boolean isValidEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }
}
