package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.memberdto.MemberResponseDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.SignupRequestDTO;
import cheongchul.cheongchul_eolam.dto.memberdto.UpdateMemberDTO;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();

        Member member = new Member("test@example.com", "Test User");
        member.setPassword(passwordEncoder.encode("password123"));
        memberRepository.save(member);
    }
    @AfterEach
    public void clearRepository() {
        memberRepository.deleteAll();
    }

    @Test
    void testRegister() {

        SignupRequestDTO signupRequestDTO = new SignupRequestDTO("new@example.com","newPassword123", "New User","NewU","teacher","서운대","영문학과" );
        MemberResponseDTO memberResponseDTO = authService.register(signupRequestDTO);

        assertThat(memberResponseDTO.getEmail()).isEqualTo("new@example.com");
        assertThat(memberResponseDTO.getName()).isEqualTo("New User");
    }

    @Test
    void testFindById() {
        Member member = memberRepository.findAll().get(0);
        MemberResponseDTO memberResponseDTO = memberService.findById(member.getMemberId());

        assertThat(memberResponseDTO.getEmail()).isEqualTo(member.getEmail());
        assertThat(memberResponseDTO.getName()).isEqualTo(member.getName());
    }
    @Test
    void testUpdateMember() {
        Member member = memberRepository.findAll().get(0);
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO("Updated Nickname", "Updated Role", "Updated University","Updated Department" );

        MemberResponseDTO updatedMember = memberService.updatedMember(member.getMemberId(), updateMemberDTO);

        assertThat(updatedMember.getNickname()).isEqualTo("Updated Nickname");
        assertThat(updatedMember.getUniversity()).isEqualTo("Updated University");
        assertThat(updatedMember.getDepartment()).isEqualTo("Updated Department");
        assertThat(updatedMember.getRole()).isEqualTo("Updated Role");
    }

    @Test
    void testUpdatePassword() {
        Member member = memberRepository.findAll().get(0);
        memberService.updatedPassword(member.getMemberId(), "password123", "newPassword123");
        assertThat(passwordEncoder.matches("newPassword123", member.getPassword())).isTrue();
    }

    @Test
    void testDeleteMember() {
        Member member = memberRepository.findAll().get(0);
        memberService.deleteMember(member.getMemberId());

        assertThrows(NoSuchElementException.class, () -> {
            memberRepository.findById(member.getMemberId()).orElseThrow(()-> new NoSuchElementException());
        });
    }
}
