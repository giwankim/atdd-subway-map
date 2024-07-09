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

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineSections {
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "line_id")
  private final List<LineSection> sections = new ArrayList<>();

  @Builder
  public LineSections(List<LineSection> lineSections) {
    this.sections.addAll(lineSections);
  }

  public LineSections(LineSection... lineSections) {
    this.sections.addAll(Arrays.asList(lineSections));
  }

  public boolean isEmpty() {
    return sections.isEmpty();
  }

  public LineSection getFirst() {
    return sections.get(0);
  }

  public LineSection getLast() {
    return sections.get(sections.size() - 1);
  }

  public void add(LineSection lineSection) {
    sections.add(lineSection);
  }

  public void addAll(LineSections lineSections) {
    this.sections.addAll(lineSections.sections);
  }

  public List<Station> getStations() {
    if (sections.isEmpty()) {
      return Collections.emptyList();
    }
    List<Station> stations =
        sections.stream().map(LineSection::getUpStation).collect(Collectors.toList());
    stations.add(sections.get(sections.size() - 1).getDownStation());
    return Collections.unmodifiableList(stations);
  }
}
