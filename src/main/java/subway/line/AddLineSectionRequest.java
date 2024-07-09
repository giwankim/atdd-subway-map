package subway.line;

import lombok.Getter;

@Getter
public class AddLineSectionRequest {
  private final Long upStationId;
  private final Long downStationId;
  private final Integer distance;

  public AddLineSectionRequest(Long upStationId, Long downStationId, Integer distance) {
    this.upStationId = upStationId;
    this.downStationId = downStationId;
    this.distance = distance;
  }
}
