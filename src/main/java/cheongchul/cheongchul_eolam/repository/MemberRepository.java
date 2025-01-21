package cheongchul.cheongchul_eolam.repository;

import cheongchul.cheongchul_eolam.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}
