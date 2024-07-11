package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorCode;

public class LineNotFoundException extends ApiException {
  public LineNotFoundException(Object data) {
    super(ErrorCode.NOT_FOUND, data);
  }
}
