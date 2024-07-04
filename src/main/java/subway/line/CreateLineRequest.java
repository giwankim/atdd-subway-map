package subway.line;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateLineRequest {
  private final String name;
  private final String color;
  private final Integer upStationId;
  private final Integer downStationId;
  private final Integer distance;

  @Builder
  public CreateLineRequest(String name, String color, Integer upStationId, Integer downStationId, Integer distance) {
    this.name = name;
    this.color = color;
    this.upStationId = upStationId;
    this.downStationId = downStationId;
    this.distance = distance;
  }
}
