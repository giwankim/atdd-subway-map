package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineRemover {
  private final LineRepository lineRepository;

  public void remove(Long id) {
    lineRepository.deleteById(id);
  }
}
