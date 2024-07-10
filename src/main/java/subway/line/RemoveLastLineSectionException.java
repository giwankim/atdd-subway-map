package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class RemoveLastLineSectionException extends ApiException {
  public RemoveLastLineSectionException() {
    super(ErrorType.BAD_REQUEST);
  }
}
