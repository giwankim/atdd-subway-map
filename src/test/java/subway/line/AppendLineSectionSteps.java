package subway.line;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.line.LineAcceptanceSteps.지하철_노선_조회_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AppendLineSectionSteps {
  private AppendLineSectionSteps() {}

  public static ExtractableResponse<Response> 노선_구간_등록_요청(
      long lineId, AppendLineSectionRequest request) {
    return RestAssured.given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/lines/" + lineId + "/sections")
        .then()
        .log()
        .all()
        .extract();
  }

  public static void 노선_구간_등록됨(
      ExtractableResponse<Response> response, long lineId, long downStationId) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    ExtractableResponse<Response> lineResponse = 지하철_노선_조회_요청("/lines/" + lineId);
    List<Long> stationIds = lineResponse.jsonPath().getList("stations.id", Long.class);
    assertThat(stationIds.get(stationIds.size() - 1)).isEqualTo(downStationId);
  }

  public static void 노선_구간_요청_실패함(ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }
}
