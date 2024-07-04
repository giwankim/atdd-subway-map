package subway.line;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class LineAcceptanceSteps {
  private LineAcceptanceSteps() {
  }

  public static ExtractableResponse<Response> 지하철_노선_생성_요청(CreateLineRequest request) {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/lines")
        .then().log().all()
        .extract();
  }

  public static void 지하철_노선_생성됨(ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank();
  }

  public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
    return RestAssured.given().log().all()
        .when().get("/lines")
        .then().log().all()
        .extract();
  }

  public static void 지하철_노선_목록_조회됨(ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  public static void 지하철_노선_목록에_포함됨(ExtractableResponse<Response> response, String... names) {
    assertThat(response.jsonPath().getList("name", String.class)).containsExactlyInAnyOrder(names);
  }
}
