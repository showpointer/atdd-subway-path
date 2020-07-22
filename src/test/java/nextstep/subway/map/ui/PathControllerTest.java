package nextstep.subway.map.ui;

import nextstep.subway.map.application.PathService;
import nextstep.subway.map.domain.ShortestPathEnum;
import nextstep.subway.map.dto.PathRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PathControllerTest {
    // given
    PathService pathService;
    PathController pathController;

    @BeforeEach
    void setUp() {
        pathService = mock(PathService.class);
        pathController = new PathController(pathService);
    }

    @DisplayName("최단 경로 조회 컨트롤러 테스트")
    @Test
    void findShortestDistancePath() {
        // given
        PathRequest request = new PathRequest(1L, 3L, ShortestPathEnum.DISTANCE.getType());

        // when
        ResponseEntity response = pathController.findShortestPath(request);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pathService).findShortestPath(request);
    }

    @DisplayName("최소 시간 경로 조회 컨트롤러 테스트")
    @Test
    void findShortestDurationPath() {
        // given
        PathRequest request = new PathRequest(1L, 3L, ShortestPathEnum.DURATION.getType());

        // when
        ResponseEntity response = pathController.findShortestPath(request);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pathService).findShortestPath(request);
    }
}