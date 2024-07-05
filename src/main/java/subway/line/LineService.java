package subway.line;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LineService {
  private final LineAppender lineAppender;
  private final LineReader lineReader;

  public Line saveLine(CreateLineRequest createLineRequest) {
    return lineAppender.append(createLineRequest);
  }

  public List<Line> findAllLines() {
    return lineReader.read();
  }
}
