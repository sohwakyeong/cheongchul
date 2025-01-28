package cheongchul.cheongchul_eolam.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "잘못된 요청"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED","인증 실패"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"NOT_FOUND","찾을 수 없음"),
    CONFLICT(HttpStatus.CONFLICT, "CONFLICT", "중복 요청"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"SERVER_ERROR","서버 오류");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
