package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import subway.support.AcceptanceTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
    ExtractableResponse<Response> response = givenStationCreated("강남역");

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

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
    givenStationCreated("강남역");
    givenStationCreated("역삼역");

    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .when().get("/stations")
        .then().log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    List<String> stationNames = response.jsonPath().getList("name", String.class);
    assertThat(stationNames).containsExactlyInAnyOrder("강남역", "역삼역");
  }

  /**
   * Given 지하철역을 생성하고
   * When 그 지하철역을 삭제하면
   * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
   */
  // TODO: 지하철역 제거 인수 테스트 메서드 생성

  private static ExtractableResponse<Response> givenStationCreated(String name) {
    Map<String, String> params = new HashMap<>();
    params.put("name", name);

    return RestAssured.given().log().all()
        .body(params)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().post("/stations")
        .then().log().all()
        .extract();
  }
}
