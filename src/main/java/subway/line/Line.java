package subway.line;

import java.util.Arrays;
import java.util.List;
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

  @Embedded private final LineSections lineSections = new LineSections();

  @Builder
  public Line(Long id, String name, String color, LineSections lineSections) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.lineSections.addAll(lineSections);
  }

  public Line(String name, String color, LineSection... sections) {
    this(null, name, color, new LineSections(Arrays.asList(sections)));
  }

  public Line(String name, String color) {
    this(null, name, color, new LineSections());
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void changeColor(String color) {
    this.color = color;
  }

  public void addSection(LineSection section) {
    lineSections.add(section);
  }

  public List<Station> getStations() {
    return lineSections.getStations();
  }
}
