package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import subway.station.Station;

@Component
@RequiredArgsConstructor
public class RemoveLineSectionValidator {
  public void validate(LineSections lineSections, Station station) {
    if (!isTerminalStation(lineSections, station)) {
      throw new RemoveNonTerminalStationException();
    }
    if (lineSections.size() <= 1) {
      throw new RemoveLastLineSectionException();
    }
  }

  private static boolean isTerminalStation(LineSections lineSections, Station station) {
    return lineSections.getLast().getDownStation().isSame(station);
  }
}
