package subway.line;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AppendSectionRequest {
  private final int upStationId;
  private final int downStationId;
  private final int distance;
}
