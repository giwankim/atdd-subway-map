package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class LineSectionNotAppendableException extends ApiException {
  public LineSectionNotAppendableException() {
    super(ErrorType.BAD_REQUEST);
  }
}
