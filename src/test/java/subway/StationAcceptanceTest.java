package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import subway.support.AcceptanceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.StationAcceptanceSteps.*;

@DisplayName("지하철역 관련 기능")
class StationAcceptanceTest extends AcceptanceTest {
  /**
   * When 지하철역을 생성하면
   * Then 지하철역이 생성된다
   * Then 지하철역 목록 조회 시 생성한 역을 찾을 수 있다
   */
  @DisplayName("지하철역을 생성한다.")
  @Test
  void createStation() {
    // when
    ExtractableResponse<Response> response = 지하철역_생성_요청("강남역");

    // then
    지하철역_생성됨(response);

    // then
    List<String> stationNames =
        RestAssured.given().log().all()
            .when().get("/stations")
            .then().log().all()
            .extract().jsonPath().getList("name", String.class);
    assertThat(stationNames).containsAnyOf("강남역");
  }

  /**
   * Given 2개의 지하철역을 생성하고
   * When 지하철역 목록을 조회하면
   * Then 2개의 지하철역을 응답 받는다
   */
  @DisplayName("지하철역 목록을 조회한다.")
  @Test
  void showStations() {
    지하철역_생성_요청("강남역");
    지하철역_생성_요청("역삼역");

    ExtractableResponse<Response> response = 지하철역_목록_조회_요청();

    지하철역_목록_조회됨(response);

    List<String> stationNames = response.jsonPath().getList("name", String.class);
    assertThat(stationNames).containsExactlyInAnyOrder("강남역", "역삼역");
  }



  /**
   * Given 지하철역을 생성하고
   * When 그 지하철역을 삭제하면
   * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
   */
  @DisplayName("지하철역을 삭제한다.")
  @Test
  void deleteStation() {
    ExtractableResponse<Response> createResponse = 지하철역_생성_요청("강남역");
    String uri = createResponse.header(HttpHeaders.LOCATION);

    ExtractableResponse<Response> response = 지하철역_삭제_요청(uri);

    지하철역_삭제됨(response);

    ExtractableResponse<Response> stationsResponse = 지하철역_목록_조회_요청();
    List<String> stationNames = stationsResponse.jsonPath().getList("name", String.class);
    assertThat(stationNames).doesNotContain("강남역").isEmpty();
  }
}
