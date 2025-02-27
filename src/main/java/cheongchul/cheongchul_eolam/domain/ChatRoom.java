package cheongchul.cheongchul_eolam.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    private Long me;
    private Long other;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatMessage> chats = new ArrayList<>();

    public ChatRoom (Long me,Long other) {
        this.me = me;
        this.other = other;
    }
    public void addChatMessage(ChatMessage chatMessage) {
        chats.add(chatMessage);
        chatMessage.setChatRoom(this);
    }
}
