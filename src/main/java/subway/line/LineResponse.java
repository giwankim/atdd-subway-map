package subway.line;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import subway.station.Station;

@Getter
@EqualsAndHashCode
@ToString
public class LineResponse {
  private final Long id;
  private final String name;
  private final String color;
  private final List<LineStationResponse> stations;

  public LineResponse(Long id, String name, String color, List<LineStationResponse> stations) {
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
            LineStationResponse.from(line.getUpStation()),
            LineStationResponse.from(line.getDownStation())));
  }

  public static List<LineResponse> listOf(List<Line> lines) {
    return lines.stream().map(LineResponse::from).collect(Collectors.toList());
  }

  @Getter
  @EqualsAndHashCode
  @ToString
  public static class LineStationResponse {
    private final Long id;
    private final String name;

    public LineStationResponse(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public static LineStationResponse from(Station station) {
      return new LineStationResponse(station.getId(), station.getName());
    }
  }
}
