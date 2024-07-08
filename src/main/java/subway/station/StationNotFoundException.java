package subway.station;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class StationNotFoundException extends ApiException {
  public StationNotFoundException() {
    super(ErrorType.NOT_FOUND);
  }
}
