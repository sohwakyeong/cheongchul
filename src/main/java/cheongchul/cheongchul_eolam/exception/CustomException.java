package cheongchul.cheongchul_eolam.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode code;

  public CustomException(ErrorCode code,String message){
      super(code.getMessage()+":"+message);
      this.code = code;
  }
  public CustomException(ErrorCode code) {
      super(code.getMessage());
      this.code = code;
  }
}
