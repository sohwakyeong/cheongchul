package cheongchul.cheongchul_eolam.service;

import cheongchul.cheongchul_eolam.domain.ChatMessage;
import cheongchul.cheongchul_eolam.domain.ChatRoom;
import cheongchul.cheongchul_eolam.domain.Member;
import cheongchul.cheongchul_eolam.dto.chat.ChatRoomDTO;
import cheongchul.cheongchul_eolam.dto.chat.ChatRoomListDTO;
import cheongchul.cheongchul_eolam.exception.CustomException;
import cheongchul.cheongchul_eolam.exception.ErrorCode;
import cheongchul.cheongchul_eolam.repository.ChatMessageRepository;
import cheongchul.cheongchul_eolam.repository.ChatRoomRepository;
import cheongchul.cheongchul_eolam.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomDTO findOrCreateChatRoom(long me, long other) {
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByMeAndOther(me, other);

        if (existingRoom.isEmpty()) {
            existingRoom = chatRoomRepository.findByOtherAndMe(other, me);
        }

        ChatRoom chatRoom = existingRoom.orElseGet(() -> chatRoomRepository.save(new ChatRoom(me, other)));

        Member otherUser = memberRepository.findById(other)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));

        return new ChatRoomDTO(chatRoom.getChatRoomId(), chatRoom.getMe(), otherUser.getNickname());
    }



    public List<ChatRoomListDTO> getUserChatRooms(long memberId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByMeOrOther(memberId, memberId);

        return chatRooms.stream().map(chatRoom -> {
            long otherId = chatRoom.getMe().equals(memberId) ? chatRoom.getOther() : chatRoom.getMe();
            Member otherUser = memberRepository.findById(otherId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재하지 않는 회원입니다."));

            PageRequest pageRequest = PageRequest.of(0, 1);
            Page<ChatMessage> lastMessagePage = chatMessageRepository.findLastMessageByChatRoomId(chatRoom.getChatRoomId(), pageRequest);

            String lastMessage = lastMessagePage.hasContent() ? lastMessagePage.getContent().get(0).getMessage() : null;
            LocalDateTime lastMessageTime = lastMessagePage.hasContent() ? lastMessagePage.getContent().get(0).getCreatedAt() : null;

            return new ChatRoomListDTO(
                    chatRoom.getChatRoomId(),
                    memberId,
                    otherUser.getNickname(),
                    otherUser.getUniversityImgUrl(),
                    lastMessage,
                    lastMessageTime
            );
        }).collect(Collectors.toList());
    }

}
