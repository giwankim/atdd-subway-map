package subway.support.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
  DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "서버 내부 에러가 발생했습니다."),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, ErrorCode.E400, "잘못된 요청입니다."),
  NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "존재하지 않는 정보입니다.");

  private final HttpStatus status;
  private final ErrorCode code;
  private final String message;
}
