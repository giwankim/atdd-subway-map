package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorCode;

public class RemoveLastLineSectionException extends ApiException {
  public RemoveLastLineSectionException() {
    super(ErrorCode.REMOVE_LAST_LINE_SECTION);
  }
}
