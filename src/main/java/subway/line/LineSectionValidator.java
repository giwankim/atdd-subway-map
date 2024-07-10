package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineSectionValidator {
  public void validate(LineSections lineSections, LineSection lineSection) {
    if (lineSections.isEmpty()) {
      return;
    }
    if (!isAppendable(lineSections, lineSection)) {
      throw new LineSectionNotAppendable();
    }
  }

  private static boolean isAppendable(LineSections lineSections, LineSection lineSection) {
    return lineSections.getLast().getDownStation().isSame(lineSection.getUpStation());
  }
}
