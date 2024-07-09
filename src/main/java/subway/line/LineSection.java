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
public class LineSection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "up_station_id")
  private Station upStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "down_station_id")
  private Station downStation;

  private int distance;

  @Builder
  public LineSection(Long id, Station upStation, Station downStation, int distance) {
    this.id = id;
    this.upStation = upStation;
    this.downStation = downStation;
    this.distance = distance;
  }

  public LineSection(Station upStation, Station downStation, int distance) {
    this(null, upStation, downStation, distance);
  }
}
