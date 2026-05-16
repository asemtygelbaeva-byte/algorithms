import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeightedGraph<V> {
    private final boolean undirected;
    private final Map<V, Vertex<V>> vertices;

    public WeightedGraph() {
        this(true);
    }

    public WeightedGraph(boolean undirected) {
        this.undirected = undirected;
        this.vertices = new LinkedHashMap<>();
    }

    public Vertex<V> addVertex(V data) {
        Vertex<V> vertex = vertices.get(data);
        if (vertex == null) {
            vertex = new Vertex<>(data);
            vertices.put(data, vertex);
        }
        return vertex;
    }

    public void addVertex(Vertex<V> vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        vertices.putIfAbsent(vertex.getData(), vertex);
    }

    public void addEdge(V source, V destination) {
        addEdge(source, destination, 1.0);
    }

    public void addEdge(V source, V destination, double weight) {
        Vertex<V> sourceVertex = addVertex(source);
        Vertex<V> destinationVertex = addVertex(destination);
        addEdge(sourceVertex, destinationVertex, weight);
    }

    public void addEdge(Vertex<V> source, Vertex<V> destination, double weight) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination cannot be null");
        }

        addVertex(source);
        addVertex(destination);
        source.addAdjacentVertex(destination, weight);
        if (undirected) {
            destination.addAdjacentVertex(source, weight);
        }
    }

    public Vertex<V> getVertex(V data) {
        return vertices.get(data);
    }

    public boolean hasVertex(V data) {
        return vertices.containsKey(data);
    }

    public Collection<Vertex<V>> getVertices() {
        return Collections.unmodifiableCollection(vertices.values());
    }

    public Map<Vertex<V>, Double> getAdjacentVertices(V data) {
        Vertex<V> vertex = requireVertex(data);
        return vertex.getAdjacentVertices();
    }

    public Map<Vertex<V>, Double> adjacencyList(V data) {
        return getAdjacentVertices(data);
    }

    public Map<Vertex<V>, Double> adjacencyList(Vertex<V> vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        return requireVertex(vertex.getData()).getAdjacentVertices();
    }

    public double getWeight(V source, V destination) {
        Vertex<V> sourceVertex = requireVertex(source);
        Vertex<V> destinationVertex = requireVertex(destination);
        return sourceVertex.getWeight(destinationVertex);
    }

    public int size() {
        return vertices.size();
    }

    public boolean isDirected() {
        return !undirected;
    }

    public boolean isUndirected() {
        return undirected;
    }

    Vertex<V> requireVertex(V data) {
        Vertex<V> vertex = vertices.get(data);
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex does not exist: " + data);
        }
        return vertex;
    }
}
