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
  private final LineRemover lineRemover;

  public Line saveLine(CreateLineRequest createLineRequest) {
    return lineAppender.append(createLineRequest);
  }

  public List<Line> findAllLines() {
    return lineReader.read();
  }

  public Line findLineById(Long id) {
    return lineReader.readById(id);
  }

  public Line updateLineById(Long id, UpdateLineRequest request) {
    return lineModifier.modify(id, request);
  }

  public void deleteLineById(Long id) {
    lineRemover.remove(id);
  }
}
