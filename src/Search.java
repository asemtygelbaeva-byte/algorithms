import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Search<V> {
    protected final WeightedGraph<V> graph;
    protected final Vertex<V> source;
    protected final Set<Vertex<V>> marked;
    protected final Map<Vertex<V>, Vertex<V>> edgeTo;

    protected Search(WeightedGraph<V> graph, V source) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
        this.source = graph.requireVertex(source);
        this.marked = new LinkedHashSet<>();
        this.edgeTo = new LinkedHashMap<>();
    }

    public boolean hasPathTo(V destination) {
        Vertex<V> destinationVertex = graph.getVertex(destination);
        return destinationVertex != null && marked.contains(destinationVertex);
    }

    public List<V> pathTo(V destination) {
        Vertex<V> destinationVertex = graph.getVertex(destination);
        if (destinationVertex == null || !hasPathTo(destination)) {
            return Collections.emptyList();
        }

        List<V> path = new ArrayList<>();
        for (Vertex<V> vertex = destinationVertex; vertex != null; vertex = edgeTo.get(vertex)) {
            path.add(vertex.getData());
            if (vertex.equals(source)) {
                break;
            }
        }
        Collections.reverse(path);
        return path;
    }

    public Set<V> getMarked() {
        Set<V> result = new LinkedHashSet<>();
        for (Vertex<V> vertex : marked) {
            result.add(vertex.getData());
        }
        return Collections.unmodifiableSet(result);
    }
}
