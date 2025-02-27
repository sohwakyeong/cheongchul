package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.domain.ChatMessage;
import cheongchul.cheongchul_eolam.dto.chat.ChatMessageDTO;
import cheongchul.cheongchul_eolam.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatMessageController(ChatMessageService chatMessageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        // 메시지 저장
        ChatMessage savedChat = chatMessageService.saveChat(chatMessageDTO.getChatRoomId(), chatMessageDTO.getMe(), chatMessageDTO.getMessage());

        simpMessagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getChatRoomId(), savedChat);
    }

    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessage>> getChatMessage (@PathVariable("chatRoomId") Long chatRoomId) {
      List<ChatMessage> chatMessages = chatMessageService.getMessagesByChatRoom(chatRoomId);
        return new ResponseEntity<>(chatMessages, HttpStatus.OK);
    }
}
