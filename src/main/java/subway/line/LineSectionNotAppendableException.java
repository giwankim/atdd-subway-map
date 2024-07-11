package subway.line;

import subway.support.error.ApiException;
import subway.support.error.ErrorCode;

public class LineSectionNotAppendableException extends ApiException {
  public LineSectionNotAppendableException() {
    super(ErrorCode.LINE_SECTION_NOT_APPENDABLE);
  }
}
