package cheongchul.cheongchul_eolam.repository;

import cheongchul.cheongchul_eolam.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findByMeAndOther(Long me,Long other);
    Optional<ChatRoom> findByOtherAndMe(Long other, Long me);

    List<ChatRoom> findByMeOrOther(Long me, Long other);
}
