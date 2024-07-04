package subway.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationReader {
  private final StationRepository stationRepository;

  public Station read(Long id) {
    return stationRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다."));
  }
}
