package subway.line;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LineAcceptanceSteps {
  private LineAcceptanceSteps() {}

  public static ExtractableResponse<Response> 지하철_노선_생성_요청(Line line) {
    CreateLineRequest request =
        new CreateLineRequest(
            line.getName(),
            line.getColor(),
            line.getUpStation().getId(),
            line.getDownStation().getId(),
            line.getDistance());
    return RestAssured.given()
        .log()
        .all()
        .body(request)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post("/lines")
        .then()
        .log()
        .all()
        .extract();
  }

  public static void 지하철_노선_생성됨(ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank();
  }

  public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
    return RestAssured.given().log().all().when().get("/lines").then().log().all().extract();
  }

  public static void 지하철_노선_목록에_포함됨(
      ExtractableResponse<Response> response, ExtractableResponse<Response>... createResponses) {
    List<LineResponse> actualLines = response.jsonPath().getList(".", LineResponse.class);
    List<LineResponse> expectedLines =
        Arrays.stream(createResponses)
            .map(it -> it.as(LineResponse.class))
            .collect(Collectors.toList());
    assertThat(actualLines).containsExactlyInAnyOrderElementsOf(expectedLines);
  }

  public static ExtractableResponse<Response> 지하철_노선_조회_요청(String uri) {
    return RestAssured.given().log().all().when().get(uri).then().log().all().extract();
  }

  public static void 지하철_노선_조회됨(ExtractableResponse<Response> response, Line line) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(LineResponse.class)).isEqualTo(LineResponse.from(line));
  }
}
