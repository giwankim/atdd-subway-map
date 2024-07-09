package subway.line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "line_id")
  private List<Section> sections = new ArrayList<>();

  @Builder
  public Line(Long id, String name, String color, List<Section> sections) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.sections.addAll(sections);
  }

  public Line(String name, String color, Section... sections) {
    this(null, name, color, Arrays.asList(sections));
  }

  public Line(String name, String color) {
    this(null, name, color, new ArrayList<>());
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void changeColor(String color) {
    this.color = color;
  }

  public void addSection(Section section) {
    sections.add(section);
  }

  public List<Station> getStations() {
    if (sections.isEmpty()) {
      return Collections.emptyList();
    }
    List<Station> stations =
        sections.stream().map(Section::getUpStation).collect(Collectors.toList());
    stations.add(sections.get(sections.size() - 1).getDownStation());
    return stations;
  }
}
