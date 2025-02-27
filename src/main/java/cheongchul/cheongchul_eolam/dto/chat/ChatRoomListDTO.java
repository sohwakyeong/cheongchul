package cheongchul.cheongchul_eolam.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ChatRoomListDTO {
    private long chatRoomId;
    private long me;

    private String otherNickName;
    private String otherImg;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}
