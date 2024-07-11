package subway.support;

import subway.line.Line;
import subway.line.Line.LineBuilder;
import subway.line.LineSection;
import subway.line.LineSections;
import subway.station.Station;

public class Fixtures {
  private Fixtures() {}

  public static Station gangnam() {
    return Station.builder().id(1L).name("강남역").build();
  }

  public static Station yeoksam() {
    return Station.builder().id(2L).name("역삼역").build();
  }

  public static Station pangyo() {
    return Station.builder().id(3L).name("판교역").build();
  }

  public static Station seolleung() {
    return Station.builder().id(4L).name("선릉역").build();
  }

  public static LineBuilder aLine() {
    return Line.builder().name("2호선").color("bg-green-600");
  }

  public static Line lineTwo() {
    return Line.builder()
        .id(1L)
        .name("2호선")
        .color("bg-green-600")
        .lineSections(new LineSections(gangnamToYeoksam()))
        .build();
  }

  public static Line shinBundang() {
    return Line.builder()
        .id(2L)
        .name("신분당선")
        .color("bg-red-600")
        .lineSections(new LineSections(gangnamToPangyo()))
        .build();
  }

  public static LineSection gangnamToYeoksam() {
    return LineSection.builder().upStation(gangnam()).downStation(yeoksam()).distance(10).build();
  }

  public static LineSection gangnamToPangyo() {
    return LineSection.builder().upStation(gangnam()).downStation(pangyo()).distance(20).build();
  }
}
