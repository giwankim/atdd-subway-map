package subway.support;

import subway.line.CreateLineRequest;
import subway.line.CreateLineRequest.CreateLineRequestBuilder;

public class Fixtures {
  private Fixtures() {
  }

  public static CreateLineRequestBuilder aCreateLineRequest(String name, String color) {
    return CreateLineRequest.builder()
        .name(name)
        .color(color)
        .upStationId(1)
        .downStationId(2)
        .distance(10);
  }
}
