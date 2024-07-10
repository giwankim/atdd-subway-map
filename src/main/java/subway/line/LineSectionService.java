package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LineSectionService {
  private final LineReader lineReader;
  private final LineSectionMapper lineSectionMapper;
  private final LineSectionValidator lineSectionValidator;

  @Transactional
  public Line appendLineSection(Long lineId, AppendLineSectionRequest request) {
    Line line = lineReader.readById(lineId);
    LineSection lineSection = lineSectionMapper.map(request);
    line.addLineSection(lineSection, lineSectionValidator);
    return line;
  }
}
