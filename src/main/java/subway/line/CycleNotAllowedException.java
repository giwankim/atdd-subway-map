package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class CycleNotAllowedException extends ApiException {
  public CycleNotAllowedException() {
    super(ErrorType.BAD_REQUEST);
  }
}
