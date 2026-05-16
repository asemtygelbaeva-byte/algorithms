import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex<V> {
    private final V data;
    private final Map<Vertex<V>, Double> adjacentVertices;

    public Vertex(V data) {
        this.data = data;
        this.adjacentVertices = new LinkedHashMap<>();
    }

    public V getData() {
        return data;
    }

    public Map<Vertex<V>, Double> getAdjacentVertices() {
        return Collections.unmodifiableMap(adjacentVertices);
    }

    public void addAdjacentVertex(Vertex<V> destination, double weight) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination vertex cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        adjacentVertices.put(destination, weight);
    }

    public boolean hasAdjacentVertex(Vertex<V> destination) {
        return adjacentVertices.containsKey(destination);
    }

    public double getWeight(Vertex<V> destination) {
        Double weight = adjacentVertices.get(destination);
        if (weight == null) {
            throw new IllegalArgumentException("Vertices are not connected");
        }
        return weight;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vertex<?>)) {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) other;
        return Objects.equals(data, vertex.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }
}
