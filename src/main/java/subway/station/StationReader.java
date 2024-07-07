package subway.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationReader {
  private final StationRepository stationRepository;

  public Station readById(Long id) {
    return stationRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 역 #" + id + "이 존재하지 않습니다."));
  }
}
