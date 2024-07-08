package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class LineNotFoundException extends ApiException {
  public LineNotFoundException() {
    super(ErrorType.NOT_FOUND);
  }
}
