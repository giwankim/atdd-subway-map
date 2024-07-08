package subway.station;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class StationResponse {
  private final Long id;
  private final String name;

  public StationResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static StationResponse from(Station station) {
    return new StationResponse(station.getId(), station.getName());
  }
}
