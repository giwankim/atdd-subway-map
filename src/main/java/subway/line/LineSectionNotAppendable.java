package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class LineSectionNotAppendable extends ApiException {
  public LineSectionNotAppendable() {
    super(ErrorType.BAD_REQUEST);
  }
}
