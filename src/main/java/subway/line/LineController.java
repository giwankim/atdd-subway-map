package subway.line;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LineController {
  private final LineService lineService;

  @PostMapping("/lines")
  public ResponseEntity<LineResponse> createLine(@RequestBody CreateLineRequest request) {
    Line line = lineService.saveLine(request);
    LineResponse response = LineResponse.from(line);
    return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(response);
  }
}
