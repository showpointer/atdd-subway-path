package nextstep.subway.path.ui;

import com.google.common.collect.Lists;
import nextstep.subway.path.dto.PathResponse;
import nextstep.subway.station.dto.StationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/paths")
public class PathController {

    @GetMapping
    public ResponseEntity<PathResponse> findShortestPath() {

        StationResponse stationResponse = new StationResponse(1L, "강남역", LocalDateTime.now(), LocalDateTime.now());
        List<StationResponse> stations = Lists.newArrayList(stationResponse);

        return ResponseEntity.ok(new PathResponse(stations, 4, 4));
    }
}