package cheongchul.cheongchul_eolam.controller;

import cheongchul.cheongchul_eolam.domain.ChatRoom;
import cheongchul.cheongchul_eolam.dto.chat.ChatRoomDTO;
import cheongchul.cheongchul_eolam.dto.chat.ChatRoomListDTO;
import cheongchul.cheongchul_eolam.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cheongchul.cheongchul_eolam.security.authUtils.getMemberId;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/room/{boardAuthorId}")
    public ResponseEntity<ChatRoomDTO> getOrCreateChatRoom(@PathVariable("boardAuthorId") long boardAuthorId, Authentication authentication){
        long meId = getMemberId(authentication);
        ChatRoomDTO chatRoom  = chatRoomService.findOrCreateChatRoom(meId,boardAuthorId);
        return new ResponseEntity<>(chatRoom, HttpStatus.OK);
    }
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomListDTO>> getUserChatRooms(Authentication authentication) {
        long loginUserId = getMemberId(authentication);
        List<ChatRoomListDTO> chatRooms = chatRoomService.getUserChatRooms(loginUserId);
        return new ResponseEntity<>(chatRooms,HttpStatus.OK);
    }
    @DeleteMapping("/rooms/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable("chatRoomId") long chatroomId){
        chatRoomService.deleteChatroom(chatroomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
