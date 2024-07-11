package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import subway.station.Station;
import subway.station.StationReader;

@Component
@RequiredArgsConstructor
public class LineSectionMapper {
  private final StationReader stationReader;

  public LineSection map(AppendLineSectionRequest request) {
    Station upStation = stationReader.readById(request.getUpStationId());
    Station downStation = stationReader.readById(request.getDownStationId());
    return new LineSection(upStation, downStation, request.getDistance());
  }
}
