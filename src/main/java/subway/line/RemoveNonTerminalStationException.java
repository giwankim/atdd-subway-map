package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorType;

public class RemoveNonTerminalStationException extends ApiException {
  public RemoveNonTerminalStationException() {
    super(ErrorType.BAD_REQUEST);
  }
}
