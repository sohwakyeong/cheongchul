package cheongchul.cheongchul_eolam.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomDTO {
        private long chatRoomId;
        private long me;
        private String otherNickName;
}
