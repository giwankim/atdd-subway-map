package subway.line;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AddSectionRequest {
  private final long upStationId;
  private final long downStationId;
  private final int distance;
}
