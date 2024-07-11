package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorCode;

public class RemoveNonTerminalStationException extends ApiException {
  public RemoveNonTerminalStationException() {
    super(ErrorCode.REMOVE_NON_TERMINAL_STATION);
  }
}
