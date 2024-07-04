package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LineService {
  private final LineAppender lineAppender;

  public Line saveLine(CreateLineRequest createLineRequest) {
    return lineAppender.append(createLineRequest);
  }
}
