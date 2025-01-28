package cheongchul.cheongchul_eolam.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().code(e.getCode().getCode())
                .message(e.getMessage())
                .status(e.getCode().getStatus()).build();

        log.error(errorResponse.toString());
        return ResponseEntity.status(e.getCode().getStatus()).body(errorResponse);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        String detailMessage = e.getCause().getMessage().split(":")[0];

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(detailMessage)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        log.error("RuntimeException occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
