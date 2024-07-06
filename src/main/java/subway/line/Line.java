package subway.line;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import subway.station.Station;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Line {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String color;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "up_station_id")
  private Station upStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "down_station_id")
  private Station downStation;

  private int distance;

  public Line(String name, String color, Station upStation, Station downStation, int distance) {
    this(null, name, color, upStation, downStation, distance);
  }

  @Builder
  public Line(
      Long id, String name, String color, Station upStation, Station downStation, int distance) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.upStation = upStation;
    this.downStation = downStation;
    this.distance = distance;
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void changeColor(String color) {
    this.color = color;
  }
}
