package subway.station;

import subway.support.error.ApiException;
import subway.support.error.ErrorCode;

public class StationNotFoundException extends ApiException {
  public StationNotFoundException(Object data) {
    super(ErrorCode.NOT_FOUND, data);
  }
}
