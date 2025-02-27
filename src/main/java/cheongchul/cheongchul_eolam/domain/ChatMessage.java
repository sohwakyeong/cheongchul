package cheongchul.cheongchul_eolam.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private Long senderId;
    private String message;
    private LocalDateTime createdAt;


    public ChatMessage(ChatRoom chatRoom, Long senderId, String message) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }
}
