package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.station.Station;
import subway.station.StationReader;

@Service
@RequiredArgsConstructor
public class LineSectionService {
  private final LineReader lineReader;
  private final LineSectionMapper lineSectionMapper;
  private final StationReader stationReader;

  @Transactional
  public Line appendLineSection(Long lineId, AppendLineSectionRequest request) {
    Line line = lineReader.readById(lineId);
    LineSection lineSection = lineSectionMapper.map(request);
    line.appendLineSection(lineSection);
    return line;
  }

  @Transactional
  public void removeLineSection(Long lineId, Long stationId) {
    Line line = lineReader.readById(lineId);
    Station station = stationReader.readById(stationId);
    line.removeStation(station);
  }
}
