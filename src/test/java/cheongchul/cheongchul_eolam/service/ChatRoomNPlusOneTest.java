package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.ChatMessage;
import cheongchul.cheongchul_eolam.domain.ChatRoom;
import cheongchul.cheongchul_eolam.repository.ChatMessageRepository;
import cheongchul.cheongchul_eolam.repository.ChatRoomRepository;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class ChatRoomNPlusOneTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    void ChatnPlusOne() {
        long memberId = 40;
        List<ChatRoom> chatRooms = chatRoomRepository.findByMeOrOther(memberId,memberId);
        List<Long> chatRoomIds = chatRooms.stream()
                .map(ChatRoom::getChatRoomId)
                .collect(Collectors.toList());
        List<ChatMessage> lastMessages = chatMessageRepository.findLatestMessagesByChatRoomIds(chatRoomIds);

        Map<Long,ChatMessage> lastMessageMap = new HashMap<>();
        for(ChatMessage chatMessage:lastMessages){
            Long roomId = chatMessage.getChatRoom().getChatRoomId();
            lastMessageMap.put(roomId,chatMessage);
        }
        for(ChatRoom chatRoom:chatRooms) {
            ChatMessage lastMessage = lastMessageMap.get(chatRoom.getChatRoomId());
            if (lastMessage != null) {
                System.out.println("====================");
                System.out.println("채팅방 ID: " + chatRoom.getChatRoomId() +
                        "/마지막 메시지: " + lastMessage.getMessage());
                System.out.println("====================");
            }
        }

        }

    }


