package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorCode;

public class CycleNotAllowedException extends ApiException {
  public CycleNotAllowedException() {
    super(ErrorCode.BAD_REQUEST);
  }
}
