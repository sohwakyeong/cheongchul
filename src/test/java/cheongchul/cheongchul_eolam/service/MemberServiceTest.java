package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.MemberDTO;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
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
        Member member = new Member("new@example.com", "New User");
        member.setPassword("newPassword123");
        MemberDTO memberDTO = memberService.register(member);
        assertThat(memberDTO.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    void testLogin() {
        MemberDTO memberDTO = memberService.login("test@example.com", "password123");
        assertThat(memberDTO.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindById() {
        Member member = memberRepository.findAll().get(0);
        MemberDTO memberDTO = memberService.findById(member.getMemberId());

        assertThat(memberDTO.getEmail()).isEqualTo(member.getEmail());
        assertThat(memberDTO.getName()).isEqualTo(member.getName());
    }

    @Test
    void testUpdateMember() {
        Member member = memberRepository.findAll().get(0);
        member.setNickname("Updated Nickname");

        MemberDTO updatedMember = memberService.updatedMember(member.getMemberId(), member);

        assertThat(updatedMember.getNickname()).isEqualTo("Updated Nickname");
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

        //검증 방법 생각해보기
        assertThrows(NoSuchElementException.class, () -> {
            memberRepository.findById(member.getMemberId()).orElseThrow(()-> new NoSuchElementException());
        });
    }
}
