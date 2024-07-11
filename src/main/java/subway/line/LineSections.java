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

  public int size() {
    return sections.size();
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

  public void append(LineSection lineSection) {
    validateAppend(lineSection);
    sections.add(lineSection);
  }

  public void appendAll(LineSections lineSections) {
    this.sections.addAll(lineSections.sections);
  }

  public List<Station> getStations() {
    if (sections.isEmpty()) {
      return Collections.emptyList();
    }
    List<Station> stations =
        sections.stream().map(LineSection::getUpStation).collect(Collectors.toList());
    stations.add(getLast().getDownStation());
    return Collections.unmodifiableList(stations);
  }

  public void removeLast() {
    sections.remove(sections.size() - 1);
  }

  private void validateAppend(LineSection lineSection) {
    if (isEmpty()) {
      return;
    }
    if (!isAppendable(lineSection)) {
      throw new LineSectionNotAppendableException();
    }
    if (isAppendResultInCycle(lineSection)) {
      throw new CycleNotAllowedException();
    }
  }

  private boolean isAppendable(LineSection lineSection) {
    return getLast().getDownStation().isSame(lineSection.getUpStation());
  }

  private boolean isAppendResultInCycle(LineSection lineSection) {
    List<Station> stations = getStations();
    Station downStation = lineSection.getDownStation();
    return stations.stream().anyMatch(station -> station.isSame(downStation));
  }
}
