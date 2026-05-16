import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraSearch<V> extends Search<V> {
    private final Map<Vertex<V>, Double> distTo;

    public DijkstraSearch(WeightedGraph<V> graph, V source) {
        super(graph, source);
        this.distTo = new LinkedHashMap<>();
        dijkstra();
    }

    public double distTo(V destination) {
        Vertex<V> destinationVertex = graph.getVertex(destination);
        if (destinationVertex == null) {
            return Double.POSITIVE_INFINITY;
        }
        return distTo.getOrDefault(destinationVertex, Double.POSITIVE_INFINITY);
    }

    public double distanceTo(V destination) {
        return distTo(destination);
    }

    public Map<V, Double> getDistances() {
        Map<V, Double> result = new LinkedHashMap<>();
        for (Vertex<V> vertex : graph.getVertices()) {
            result.put(vertex.getData(), distTo.getOrDefault(vertex, Double.POSITIVE_INFINITY));
        }
        return Collections.unmodifiableMap(result);
    }

    private void dijkstra() {
        for (Vertex<V> vertex : graph.getVertices()) {
            distTo.put(vertex, Double.POSITIVE_INFINITY);
        }
        distTo.put(source, 0.0);

        PriorityQueue<VertexDistance<V>> queue = new PriorityQueue<>();
        queue.add(new VertexDistance<>(source, 0.0));

        while (!queue.isEmpty()) {
            VertexDistance<V> current = queue.remove();
            Vertex<V> currentVertex = current.vertex;

            if (current.distance > distTo.get(currentVertex)) {
                continue;
            }
            marked.add(currentVertex);

            for (Map.Entry<Vertex<V>, Double> entry : currentVertex.getAdjacentVertices().entrySet()) {
                Vertex<V> neighbor = entry.getKey();
                double weight = entry.getValue();
                double newDistance = distTo.get(currentVertex) + weight;

                if (newDistance < distTo.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    distTo.put(neighbor, newDistance);
                    edgeTo.put(neighbor, currentVertex);
                    queue.add(new VertexDistance<>(neighbor, newDistance));
                }
            }
        }
    }

    private static class VertexDistance<V> implements Comparable<VertexDistance<V>> {
        private final Vertex<V> vertex;
        private final double distance;

        private VertexDistance(Vertex<V> vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexDistance<V> other) {
            return Double.compare(distance, other.distance);
        }
    }
}
