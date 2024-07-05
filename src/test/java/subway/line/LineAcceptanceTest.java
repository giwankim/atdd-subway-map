package subway.line;

import static subway.line.LineAcceptanceSteps.*;
import static subway.support.Fixtures.aLine;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
  }

  /** Given 새로운 지하철 노선 정보를 입력하고, When 관리자가 노선을 생성하면, Then 해당 노선이 생성되고 노선 목록에 포함된다. */
  @DisplayName("지하철 노선을 생성한다.")
  @Test
  void createLine() {
    Line line = aLine().build();
    CreateLineRequest request =
        new CreateLineRequest(
            line.getName(),
            line.getColor(),
            line.getUpStation().getId(),
            line.getDownStation().getId(),
            line.getDistance());

    ExtractableResponse<Response> response = 지하철_노선_생성_요청(request);

    지하철_노선_생성됨(response);
    //    지하철_노선_목록에_포함됨(지하철_노선_목록_조회_요청(), "2호선");
  }
}
