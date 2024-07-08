package subway.line;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineReader {
  private final LineRepository lineRepository;

  public List<Line> read() {
    return lineRepository.findAll();
  }

  public Line readById(Long id) {
    return lineRepository.findById(id).orElseThrow(LineNotFoundException::new);
  }
}
