package nextstep.subway.path.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.path.dto.PathResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class PathAcceptanceStep {
    public static ExtractableResponse<Response> 최단_거리_경로_조회_요청(int source, int target) {
        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                get("/paths?source="+source+"&target="+target).
                then().
                log().all().
                extract();
    }

    public static void 최단_거리_경로_응답됨(ExtractableResponse<Response> response) {
        PathResponse path = response.as(PathResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(path.getStations().size()).isNotEqualTo(0);
    }

    public static void 총_거리와_소요_시간_함께_응답됨(ExtractableResponse<Response> response) {
        PathResponse path = response.as(PathResponse.class);

        assertThat(path.getDistance()).isNotNull();
        assertThat(path.getDuration()).isNotNull();
    }

}