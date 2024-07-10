package subway.line;

import static subway.line.AppendLineSectionSteps.*;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import subway.support.AcceptanceTest;

@DisplayName("지하철 구간 등록 기능")
class AppendLineSectionAcceptanceTest extends AcceptanceTest {
  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  @BeforeEach
  protected void setUp() {
    super.setUp();
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 1, "강남역");
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 2, "역삼역");
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 3, "선릉역");
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 4, "판교역");
    jdbcTemplate.update(
        "INSERT INTO line (id, name, color) VALUES (?, ?, ?)", 1, "2호선", "bg-green-600");
    jdbcTemplate.update(
        "INSERT INTO line_section (line_id, up_station_id, down_station_id, distance) VALUES (?, ?,"
            + " ?, ?)",
        1,
        1,
        2,
        10);
  }

  /** When 구간 등록을 하면 Then 해당 노선 조회 시 등록한 구간의 하행역이 노선의 하행 종점역이다. */
  @DisplayName("지하철 구간을 등록한다.")
  @Test
  void appendLineSection() {
    long lineId = 1;
    long upStationId = 2;
    long downStationId = 3;
    int distance = 20;
    AppendLineSectionRequest request =
        new AppendLineSectionRequest(upStationId, downStationId, distance);

    ExtractableResponse<Response> response = 노선_구간_등록_요청(lineId, request);

    노선_구간_등록됨(response, lineId, downStationId);
  }

  /** Given 새로운 구간의 상행역이 노선에 등록되어있는 하행 종점역이 아니고 When 구간 등록을 하면 Then 400 Bad Request 에러가 반환된다. */
  @DisplayName("노선을 연장할 수 없는 구간을 등록 시 에러가 발생한다.")
  @Test
  void appendLineSectionNotAppendable() {
    long lineId = 1;
    long upStationId = 3;
    long downStationId = 4;
    int distance = 20;
    AppendLineSectionRequest request =
        new AppendLineSectionRequest(upStationId, downStationId, distance);

    ExtractableResponse<Response> response = 노선_구간_등록_요청(lineId, request);

    노선_구간_요청_실패함(response);
  }

  /** Given 구간의 하행 역이 이미 해당 노선에 등록되어 있으면 When 구간 등록을 하면 Then 400 Bad Request 에러가 반환된다. */
  @DisplayName("Cycle이 생길 수 있는 지하철 구간 등록 시 에러가 발생한다.")
  @Test
  void appendLineSectionCycle() {
    long lineId = 1;
    long upStationId = 2;
    long downStationId = 1;
    int distance = 20;
    AppendLineSectionRequest request =
        new AppendLineSectionRequest(upStationId, downStationId, distance);

    ExtractableResponse<Response> response = 노선_구간_등록_요청(lineId, request);

    노선_구간_요청_실패함(response);
  }
}
