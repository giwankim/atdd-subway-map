package subway.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.support.AcceptanceTest;

import static subway.line.LineAcceptanceSteps.*;
import static subway.support.Fixtures.aCreateLineRequest;

@DisplayName("지하철 노선 관리 기능")
class LineAcceptanceTest extends AcceptanceTest {
  /**
   * Given 새로운 지하철 노선 정보를 입력하고,
   * When 관리자가 노선을 생성하면,
   * Then 해당 노선이 생성되고 노선 목록에 포함된다.
   */
  @DisplayName("지하철 노선을 생성한다.")
  @Test
  void createLine() {
    CreateLineRequest request = aCreateLineRequest("2호선", "bg-green-600").build();

    ExtractableResponse<Response> response = 지하철_노선_생성_요청(request);

    지하철_노선_생성됨(response);
    지하철_노선_목록에_포함됨(지하철_노선_목록_조회_요청(), "2호선");
  }
}
