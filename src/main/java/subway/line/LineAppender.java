package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import subway.station.Station;
import subway.station.StationReader;

@Component
@RequiredArgsConstructor
public class LineAppender {
  private final LineRepository lineRepository;
  private final StationReader stationReader;

  public Line append(CreateLineRequest createLineRequest) {
    Station upStation = stationReader.readById(createLineRequest.getUpStationId());
    Station downStation = stationReader.readById(createLineRequest.getDownStationId());
    Line line =
        new Line(
            createLineRequest.getName(),
            createLineRequest.getColor(),
            upStation,
            downStation,
            createLineRequest.getDistance());
    return lineRepository.save(line);
  }
}
