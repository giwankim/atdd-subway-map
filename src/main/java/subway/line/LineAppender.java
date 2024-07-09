package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineAppender {
  private final LineRepository lineRepository;

  public Line append(Line line) {
    return lineRepository.save(line);
  }
}
