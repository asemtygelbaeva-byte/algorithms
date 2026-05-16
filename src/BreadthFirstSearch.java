import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch<V> extends Search<V> {
    public BreadthFirstSearch(WeightedGraph<V> graph, V source) {
        super(graph, source);
        bfs();
    }

    private void bfs() {
        Queue<Vertex<V>> queue = new ArrayDeque<>();
        marked.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex<V> current = queue.remove();

            for (Vertex<V> neighbor : current.getAdjacentVertices().keySet()) {
                if (!marked.contains(neighbor)) {
                    marked.add(neighbor);
                    edgeTo.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
    }
}
