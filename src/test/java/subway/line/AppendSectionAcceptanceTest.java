package subway.line;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.line.LineAcceptanceSteps.지하철_노선_조회_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import subway.support.AcceptanceTest;

@DisplayName("지하철 구간 등록 기능")
class AppendSectionAcceptanceTest extends AcceptanceTest {
  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  @BeforeEach
  protected void setUp() {
    super.setUp();
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 1, "강남역");
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 2, "역삼역");
    jdbcTemplate.update("INSERT INTO station (id, name) VALUES (?, ?)", 3, "선릉역");
    jdbcTemplate.update(
        "INSERT INTO line (id, name, color, up_station_id, down_station_id, distance) VALUES (?, ?,"
            + " ?, ?, ?, ?)",
        1,
        "2호선",
        "bg-green-600",
        1,
        2,
        10);
  }

  /** When 구간 등록을 하면 Then 해당 노선 조회 시 등록한 구간의 하행역이 노선의 하행 종점역이다. */
  @DisplayName("지하철 구간을 등록한다.")
  @Test
  void appendSection() {
    int upStationId = 2;
    int downStationId = 3;
    int distance = 20;
    AppendSectionRequest request = new AppendSectionRequest(upStationId, downStationId, distance);

    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/lines/1/sections")
            .then()
            .log()
            .all()
            .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    ExtractableResponse<Response> lineResponse = 지하철_노선_조회_요청("/lines/1");
    List<Integer> stationIds = lineResponse.jsonPath().getList("stations.id", Integer.class);
    assertThat(stationIds.get(stationIds.size() - 1)).isEqualTo(downStationId);
  }

  /** Given 새로운 구간의 상행역이 노선에 등록되어있는 하행 종점역이 아니고 When 구간 등록을 하면 Then 400 에러가 반환된다. */
  @DisplayName("노선을 연장할 수 없는 구간을 등록 시 에러가 발생한다.")
  @Test
  void appendSectionNotAppendable() {
    throw new UnsupportedOperationException("테스트 작성 중");
  }

  /** Given 구간의 하행 역이 이미 해당 노선에 등록되어 있으면 When 구간 등록을 하면 Then 400 에러가 반환된다. */
  @DisplayName("Cycle이 생길 수 있는 지하철 구간 등록 시 에러가 발생한다.")
  @Test
  void appendSectionCycle() {
    throw new UnsupportedOperationException("테스트 작성 중");
  }
}
