package subway.support;

import subway.line.Line;
import subway.line.LineSection;
import subway.line.LineSections;
import subway.station.Station;

public class Fixtures {
  private Fixtures() {}

  public static Station gangnamStation() {
    return Station.builder().id(1L).name("강남역").build();
  }

  public static Station yeoksamStation() {
    return Station.builder().id(2L).name("역삼역").build();
  }

  public static Station pangyoStation() {
    return Station.builder().id(3L).name("판교역").build();
  }

  public static Line lineTwo() {
    return Line.builder()
        .id(1L)
        .name("2호선")
        .color("bg-green-600")
        .lineSections(new LineSections(aLineTwoSection()))
        .build();
  }

  public static Line shinBundangLine() {
    return Line.builder()
        .id(2L)
        .name("신분당선")
        .color("bg-red-600")
        .lineSections(new LineSections(shinBundangLineSection()))
        .build();
  }

  public static LineSection aLineTwoSection() {
    return LineSection.builder()
        .upStation(gangnamStation())
        .downStation(yeoksamStation())
        .distance(10)
        .build();
  }

  public static LineSection shinBundangLineSection() {
    return LineSection.builder()
        .upStation(gangnamStation())
        .downStation(pangyoStation())
        .distance(20)
        .build();
  }
}
