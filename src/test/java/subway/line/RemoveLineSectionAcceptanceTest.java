package subway.line;

import static subway.line.RemoveLineSectionSteps.구간_삭제_요청;
import static subway.line.RemoveLineSectionSteps.구간_삭제됨;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import subway.support.AcceptanceTest;

@DisplayName("지하철 구간 제거 기능")
class RemoveLineSectionAcceptanceTest extends AcceptanceTest {
  @Autowired JdbcTemplate jdbcTemplate;

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
    jdbcTemplate.update(
        "INSERT INTO line_section (line_id, up_station_id, down_station_id, distance) VALUES (?, ?,"
            + " ?, ?)",
        1,
        2,
        3,
        20);
  }

  /** Given 노선에 등록된 구간이 존재하고 When 종점역 구간 제거를 요청하면 Then 해당 노선 조회 시 등록한 구간의 하행 종점역에서 제외된다. */
  @DisplayName("지하철 구간을 제거한다.")
  @Test
  void removeLineSection() {
    long lineId = 1;
    long stationId = 3;
    String uri = String.format("/lines/%d/sections", lineId);

    ExtractableResponse<Response> response = 구간_삭제_요청(stationId, uri);

    구간_삭제됨(response, lineId, stationId);
  }
}
