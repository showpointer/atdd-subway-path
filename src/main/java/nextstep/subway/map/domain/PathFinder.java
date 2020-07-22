package nextstep.subway.map.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineStation;
import nextstep.subway.line.domain.LineStations;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PathFinder {
    public List<LineStation> findShortestPath(List<Line> lines, Long source, Long target) {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        Map<Long, LineStation> lineStationsWithId = getLineStationsWithId(lines);

        addVertexs(lineStationsWithId, graph);
        addEdges(lineStationsWithId, graph);

        List<Long> shortestPathIds = getShortestPath(source, target, graph);

        return toLineStations(lineStationsWithId, shortestPathIds);
    }

    private Map<Long, LineStation> getLineStationsWithId(List<Line> lines) {
        List<LineStations> lineStations = lines.stream()
                .map(Line::getLineStations)
                .collect(Collectors.toList());

        return lineStations
                .stream()
                .flatMap(it -> it.getLineStations().stream())
                .collect(Collectors.toMap(LineStation::getStationId, Function.identity(), (it1, it2) -> it1));
    }

    private void addVertexs(Map<Long, LineStation> lineStationsWithId, WeightedMultigraph<Long, DefaultWeightedEdge> graph) {
        lineStationsWithId.forEach((id, it) -> {
            graph.addVertex(id);
        });
    }

    private void addEdges(Map<Long, LineStation> lineStationsWithId, WeightedMultigraph<Long, DefaultWeightedEdge> graph) {
        lineStationsWithId.forEach((id, it) -> {
            if (it.getPreStationId() != null) {
                graph.setEdgeWeight(graph.addEdge(it.getStationId(), it.getPreStationId()), it.getDistance());
            }
        });
    }

    private List<Long> getShortestPath(Long source, Long target, Graph<Long, DefaultWeightedEdge> graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<Long, DefaultWeightedEdge>(graph);

        return dijkstraShortestPath.getPath(source, target) == null ? new ArrayList<>() : dijkstraShortestPath.getPath(source, target).getVertexList();
    }

    private List<LineStation> toLineStations(Map<Long, LineStation> lineStationsWithId, List<Long> shortestPathIds) {
        return shortestPathIds.stream()
                .map(lineStationsWithId::get)
                .collect(Collectors.toList());
    }

    public Object findShortestDurationPath(List<Line> lines, Long source, Long target) {
        return null;
    }
}