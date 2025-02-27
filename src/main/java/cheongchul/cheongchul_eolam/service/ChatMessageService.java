package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.ChatMessage;
import cheongchul.cheongchul_eolam.domain.ChatRoom;
import cheongchul.cheongchul_eolam.exception.CustomException;
import cheongchul.cheongchul_eolam.exception.ErrorCode;
import cheongchul.cheongchul_eolam.repository.ChatMessageRepository;
import cheongchul.cheongchul_eolam.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getMessagesByChatRoom(Long chatRoomId) {
        return chatMessageRepository.findMessagesByChatRoomId(chatRoomId);
    }
    @Transactional
    public ChatMessage saveChat(Long chatRoomId, Long me, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(()->  new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 채팅입니다."));
        ChatMessage chat = new ChatMessage(chatRoom, me, message);
        chatRoom.addChatMessage(chat);

        chatMessageRepository.save(chat);
        return chat; // 저장된 채팅 메시지 반환
    }
}
