package subway.support.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
  private final String code;
  private final String message;

  private ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static ErrorResponse from(ErrorType errorType) {
    return new ErrorResponse(errorType.getCode().name(), errorType.getMessage());
  }
}
