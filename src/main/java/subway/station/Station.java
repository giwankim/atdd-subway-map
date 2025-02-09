package subway.station;

import javax.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Station {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20, nullable = false)
  private String name;

  @Builder
  public Station(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Station(String name) {
    this(null, name);
  }

  public boolean isSame(Station other) {
    return this.id.equals(other.id);
  }
}
