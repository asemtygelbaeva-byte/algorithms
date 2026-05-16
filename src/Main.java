public class Main {
    public static void main(String[] args) {
        WeightedGraph<String> graph = new WeightedGraph<>();

        graph.addEdge("Almaty", "Astana", 7.0);
        graph.addEdge("Almaty", "Taraz", 2.0);
        graph.addEdge("Almaty", "Shymkent", 3.0);
        graph.addEdge("Taraz", "Kyzylorda", 10.0);
        graph.addEdge("Shymkent", "Kyzylorda", 3.0);
        graph.addEdge("Astana", "Kostanay", 4.0);

        BreadthFirstSearch<String> bfs = new BreadthFirstSearch<>(graph, "Almaty");
        DijkstraSearch<String> dijkstra = new DijkstraSearch<>(graph, "Almaty");

        System.out.println("BFS path from Almaty to Kyzylorda:");
        System.out.println(bfs.pathTo("Kyzylorda"));

        System.out.println("Dijkstra path from Almaty to Kyzylorda:");
        System.out.println(dijkstra.pathTo("Kyzylorda"));
        System.out.println("Shortest distance: " + dijkstra.distTo("Kyzylorda"));

        Assignment4Test.runAll();
    }
}
