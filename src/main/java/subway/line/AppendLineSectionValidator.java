package subway.line;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import subway.station.Station;

@Component
@RequiredArgsConstructor
public class AppendLineSectionValidator {
  public void validate(LineSections lineSections, LineSection lineSection) {
    if (lineSections.isEmpty()) {
      return;
    }
    if (!isAppendable(lineSections, lineSection)) {
      throw new LineSectionNotAppendable();
    }
    if (isCycle(lineSections, lineSection)) {
      throw new CycleNotAllowedException();
    }
  }

  private static boolean isAppendable(LineSections lineSections, LineSection lineSection) {
    return lineSections.getLast().getDownStation().isSame(lineSection.getUpStation());
  }

  private boolean isCycle(LineSections lineSections, LineSection lineSection) {
    List<Station> stations = lineSections.getStations();
    Station downStation = lineSection.getDownStation();
    return stations.stream().anyMatch(station -> station.isSame(downStation));
  }
}
