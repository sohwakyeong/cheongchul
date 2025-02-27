package cheongchul.cheongchul_eolam.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    private long chatRoomId;
    private long me;
    private String message;
}
