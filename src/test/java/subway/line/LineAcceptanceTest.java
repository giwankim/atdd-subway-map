package subway.line;

import static subway.line.LineAcceptanceSteps.*;
import static subway.support.Fixtures.lineTwo;
import static subway.support.Fixtures.shinBundangLine;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import subway.support.AcceptanceTest;

@DisplayName("지하철 노선 관리 기능")
class LineAcceptanceTest extends AcceptanceTest {
  @Autowired private JdbcTemplate jdbcTemplate;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    String sql = "INSERT INTO station (id, name) VALUES (?, ?)";
    jdbcTemplate.update(sql, 1, "강남역");
    jdbcTemplate.update(sql, 2, "역삼역");
    jdbcTemplate.update(sql, 3, "판교역");
  }

  /** Given 새로운 지하철 노선 정보를 입력하고, When 관리자가 노선을 생성하면, Then 해당 노선이 생성되고 노선 목록에 포함된다. */
  @DisplayName("지하철 노선을 생성한다.")
  @Test
  void createLine() {
    Line line = lineTwo();

    ExtractableResponse<Response> response = 지하철_노선_생성_요청(line);

    지하철_노선_생성됨(response);
    지하철_노선_목록에_포함됨(지하철_노선_목록_조회_요청(), Collections.singletonList(response));
  }

  /** Given 여러 개의 지하철 노선이 등록되어 있고, When 관리자가 지하철 노선 목록을 조회하면, Then 모든 지하철 노선 목록이 반환된다. */
  @DisplayName("지하철 노선 목록을 조회한다.")
  @Test
  void showLines() {
    ExtractableResponse<Response> lineTwo = 지하철_노선_생성_요청(lineTwo());
    ExtractableResponse<Response> shinBundang = 지하철_노선_생성_요청(shinBundangLine());

    ExtractableResponse<Response> response = 지하철_노선_목록_조회_요청();

    지하철_노선_목록에_포함됨(response, Arrays.asList(lineTwo, shinBundang));
  }

  /** Given: 특정 지하철 노선이 등록되어 있고, When: 관리자가 해당 노선을 조회하면, Then: 해당 노선의 정보가 반환된다. */
  @DisplayName("지하철 노선을 조회한다.")
  @Test
  void showLine() {
    Line line = lineTwo();
    ExtractableResponse<Response> createResponse = 지하철_노선_생성_요청(line);
    String uri = createResponse.header(HttpHeaders.LOCATION);

    ExtractableResponse<Response> response = 지하철_노선_조회_요청(uri);

    지하철_노선_조회됨(response, line);
  }

  /** Given: 특정 지하철 노선이 등록되어 있고, When: 관리자가 해당 노선을 수정하면, Then: 해당 노선의 정보가 수정된다. */
  @DisplayName("지하철 노선을 수정한다.")
  @Test
  void updateLine() {
    String newName = "다른분당선";
    String newColor = "bg-orange-600";
    ExtractableResponse<Response> createResponse = 지하철_노선_생성_요청(shinBundangLine());
    String uri = createResponse.header(HttpHeaders.LOCATION);

    지하철_노선_수정_요청(uri, newName, newColor);

    지하철_노선_수정됨(uri, newName, newColor);
  }
}
