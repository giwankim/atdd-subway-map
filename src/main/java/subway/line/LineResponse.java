package subway.line;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import subway.station.StationResponse;

@Getter
@EqualsAndHashCode
@ToString
public class LineResponse {
  private final Long id;
  private final String name;
  private final String color;
  private final List<StationResponse> stations;

  public LineResponse(Long id, String name, String color, List<StationResponse> stations) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.stations = stations;
  }

  public static LineResponse from(Line line) {
    return new LineResponse(
        line.getId(),
        line.getName(),
        line.getColor(),
        Arrays.asList(
            StationResponse.from(line.getUpStation()),
            StationResponse.from(line.getDownStation())));
  }

  public static List<LineResponse> listOf(List<Line> lines) {
    return lines.stream().map(LineResponse::from).collect(Collectors.toList());
  }
}
