package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineModifier {
  private final LineReader lineReader;
  private final LineRepository lineRepository;

  public Line modify(Long id, UpdateLineRequest request) {
    Line line = lineReader.readById(id);
    line.changeName(request.getName());
    line.changeColor(request.getColor());
    return lineRepository.save(line);
  }
}
