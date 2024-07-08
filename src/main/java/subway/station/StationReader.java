package subway.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationReader {
  private final StationRepository stationRepository;

  public Station readById(Long id) {
    return stationRepository.findById(id).orElseThrow(StationNotFoundException::new);
  }
}
