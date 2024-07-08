package subway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.support.error.ApiException;
import subway.support.error.ErrorResponse;
import subway.support.error.ErrorType;

@RestControllerAdvice
public class ApiControllerAdvice {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
    logger.warn("ApiException : {}", e.getMessage(), e);
    return new ResponseEntity<>(ErrorResponse.from(e.getErrorType()), e.getErrorType().getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    logger.error("Exception : {}", e.getMessage(), e);
    return new ResponseEntity<>(
        ErrorResponse.from(ErrorType.DEFAULT), ErrorType.DEFAULT.getStatus());
  }
}
