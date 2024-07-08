package subway.support.error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  private final ErrorType errorType;
  private final Object data;

  public ApiException(ErrorType errorType, Object data) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.data = data;
  }

  public ApiException(ErrorType errorType) {
    this(errorType, null);
  }
}
