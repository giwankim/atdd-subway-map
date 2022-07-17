package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.applicaion.dto.LineRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 노선 관련 기능")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LineAcceptanceTest {
    private static final String LINE_NAME_5 = "5호선";
    private static final String LINE_COLOR_5 = "#996CAC";
    private static final String LINE_NAME_5_UP = "5호선 상행선";
    private static final String LINE_COLOR_5_UP = "#996CAD";
    private static final String LINE_NAME_9 = "9호선";
    private static final String LINE_COLOR_9 = "#BDB092";

    private static final LineRequest LINE_5 = new LineRequest(
            LINE_NAME_5, LINE_COLOR_5, 1L, 3L, 48L);
    private static final LineRequest LINE_2 = new LineRequest(
            LINE_NAME_9, LINE_COLOR_9, 2L, 4L, 37L);

    private final LineAcceptanceTestUtils utils = new LineAcceptanceTestUtils();

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    /**
     * When 지하철 노선을 생성하면
     * Then 지하철 노선이 생성된다
     * Then 지하철 노선 목록 조회 시 생성한 노선을 찾을 수 있다
     */
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        ExtractableResponse<Response> response = utils.지하철_노선_생성(LINE_5);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().get("name").equals(LINE_NAME_5)).isTrue();

        // then
        List<String> lineNames = utils.지하철_노선_목록_조회()
                .jsonPath().getList("name", String.class);
        assertThat(lineNames).containsAnyOf(LINE_NAME_5);
    }

    /**
     * Given 2개의 지하철 노선을 생성하고
     * When 지하철 노선 목록을 조회하면
     * Then 지하철 노선 목록 조회 시 2개의 노선을 조회할 수 있다.
     */
    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void showAllLines() {
        // given
        utils.지하철_노선_생성(LINE_5);
        utils.지하철_노선_생성(LINE_2);

        // when
        ExtractableResponse<Response> response = utils.지하철_노선_목록_조회();

        // then
        assertThat(response.jsonPath().getList("name").size()).isEqualTo(2);
        assertThat(response.jsonPath().getList("name")).containsExactly(LINE_5.getName(), LINE_2.getName());
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 조회하면
     * Then 생성한 지하철 노선의 정보를 응답받을 수 있다.
     */
    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void findLine() {
        // given
        int id = utils.지하철_노선_생성(LINE_5).jsonPath().getInt("id");
        utils.지하철_노선_생성(LINE_2);

        // when
        ExtractableResponse<Response> response = utils.지하철_노선_조회(id);

        // then
        assertThat(response.jsonPath().get("name").equals(LINE_5.getName())).isTrue();
        assertThat(response.jsonPath().get("color").equals(LINE_5.getColor())).isTrue();
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성되지 않은 지하철 노선을 조회하면
     * Then NoSuchElementException 와 ErrorResponse 를 응답받을 수 있다.
     */
    @DisplayName("존재하지 않는 지하철 노선을 조회한다.")
    @Test
    void findLineNoSuchElementException() {
        // given
        utils.지하철_노선_생성(LINE_5);

        // when
        ExtractableResponse<Response> response = utils.지하철_노선_조회(2);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("errorName")).isEqualTo(NoSuchElementException.class.getName());
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 수정하면
     * Then 해당 지하철 노선 정보는 수정된다
     */
    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        // given
        int id = utils.지하철_노선_생성(LINE_5).jsonPath().getInt("id");
        LineRequest request = LineRequest.builder()
                .name(LINE_NAME_5_UP)
                .color(LINE_COLOR_5_UP)
                .build();

        // when
        ExtractableResponse<Response> updatedResponse = utils.지하철_노선_수정(id, request);
        ExtractableResponse<Response> response = utils.지하철_노선_조회(id);

        // then
        assertThat(updatedResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().get("name").equals(request.getName())).isTrue();
        assertThat(response.jsonPath().get("color").equals(request.getColor())).isTrue();
        assertThat(response.jsonPath().getInt("distance")).isEqualTo(LINE_5.getDistance().intValue());
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 생성한 지하철 노선을 삭제하면
     * Then 해당 지하철 노선 정보는 삭제된다
     */
    @DisplayName("지하철 노선을 삭제한다.")
    @Test
    void deleteLine() {
        // given
        int id = utils.지하철_노선_생성(LINE_5).jsonPath().getInt("id");
        utils.지하철_노선_생성(LINE_2);

        // when
        utils.지하철_노선_삭제(id);

        // then
        List<Integer> ids = utils.지하철_노선_목록_조회().jsonPath().getList("id");
        assertThat(ids.stream()
                .filter(lineId -> lineId == id)
                .count())
                .isEqualTo(0);
    }
}
