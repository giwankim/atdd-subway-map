package subway.line;

import static subway.line.AppendLineSectionSteps.지하철_구간_등록됨;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
  void appendSection() {
    long lineId = 1;
    long upStationId = 2;
    long downStationId = 3;
    int distance = 20;
    AppendLineSectionRequest request =
        new AppendLineSectionRequest(upStationId, downStationId, distance);

    ExtractableResponse<Response> response = AppendLineSectionSteps.지하철_구간_등록_요청(lineId, request);

    지하철_구간_등록됨(response, lineId, downStationId);
  }

  /** Given 새로운 구간의 상행역이 노선에 등록되어있는 하행 종점역이 아니고 When 구간 등록을 하면 Then 400 Bad Request 에러가 반환된다. */
  @DisplayName("노선을 연장할 수 없는 구간을 등록 시 에러가 발생한다.")
  @Test
  @Disabled
  void appendSectionNotAppendable() {
    throw new UnsupportedOperationException("테스트 작성 중");
  }

  /** Given 구간의 하행 역이 이미 해당 노선에 등록되어 있으면 When 구간 등록을 하면 Then 400 Bad Request 에러가 반환된다. */
  @DisplayName("Cycle이 생길 수 있는 지하철 구간 등록 시 에러가 발생한다.")
  @Test
  @Disabled
  void appendSectionCycle() {
    throw new UnsupportedOperationException("테스트 작성 중");
  }
}
