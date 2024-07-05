package subway.line;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LineService {
  private final LineAppender lineAppender;
  private final LineReader lineReader;
  private final LineModifier lineModifier;

  public Line saveLine(CreateLineRequest createLineRequest) {
    return lineAppender.append(createLineRequest);
  }

  public List<Line> findAllLines() {
    return lineReader.read();
  }

  public Line findLineById(Long id) {
    return lineReader.readById(id);
  }

  public Line updateById(Long id, UpdateLineRequest request) {
    return lineModifier.modify(id, request);
  }
}
