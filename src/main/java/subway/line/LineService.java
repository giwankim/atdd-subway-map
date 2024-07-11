package subway.line;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LineService {
  private final LineAppender lineAppender;
  private final LineReader lineReader;
  private final LineModifier lineModifier;
  private final LineRemover lineRemover;
  private final LineSectionMapper lineSectionMapper;
  private final AppendLineSectionValidator appendLineSectionValidator;

  @Transactional
  public Line saveLine(CreateLineRequest request) {
    Line line = lineAppender.append(request.toLine());
    LineSection lineSection = lineSectionMapper.map(request.toAddLineSection());
    line.appendLineSection(lineSection);
    return line;
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
