package cheongchul.cheongchul_eolam.repository;

import cheongchul.cheongchul_eolam.domain.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT c FROM ChatMessage c WHERE c.chatRoom.chatRoomId = :chatRoomId ORDER BY c.createdAt ASC")
    List<ChatMessage> findMessagesByChatRoomId(@Param("chatRoomId") Long chatRoomId);

//    @Query("SELECT c FROM ChatMessage c WHERE c.chatRoom.chatRoomId = :chatRoomId ORDER BY c.createdAt DESC")
//    Page<ChatMessage> findLastMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId, Pageable pageable);

    @Query(
            "SELECT m FROM ChatMessage m " +
                    "WHERE m.chatMessageId IN (" +
                    "   SELECT MAX(m2.chatMessageId) " +
                    "   FROM ChatMessage m2 " +
                    "   WHERE m2.chatRoom.chatRoomId IN :chatRoomIds " +
                    "   GROUP BY m2.chatRoom.chatRoomId" +
                    ")"
    )
    List<ChatMessage> findLatestMessagesByChatRoomIds(@Param("chatRoomIds") List<Long> chatRoomIds);
}
