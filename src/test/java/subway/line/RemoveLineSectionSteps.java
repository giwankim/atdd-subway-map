package subway.line;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.line.LineAcceptanceSteps.지하철_노선_조회_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public class RemoveLineSectionSteps {
  private RemoveLineSectionSteps() {}

  public static ExtractableResponse<Response> 구간_삭제_요청(long stationId, String uri) {
    return RestAssured.given()
        .log()
        .all()
        .queryParam("stationId", stationId)
        .when()
        .delete(uri)
        .then()
        .log()
        .all()
        .extract();
  }

  public static void 구간_삭제됨(ExtractableResponse<Response> response, long lineId, long stationId) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    ExtractableResponse<Response> lineResponse = 지하철_노선_조회_요청("/lines/" + lineId);
    List<Long> stationIds = lineResponse.jsonPath().getList("stations.id", Long.class);
    assertThat(stationIds).isNotEmpty().doesNotContain(stationId);
  }
}
