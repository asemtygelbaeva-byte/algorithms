import java.util.List;

public class Assignment4Test {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void runAll() {
        testsPassed = 0;
        testsFailed = 0;

        testUndirectedGraph();
        testBreadthFirstSearch();
        testDijkstraSearch();
        testUnreachableVertex();

        System.out.println("Assignment 4 passed: " + testsPassed);
        System.out.println("Assignment 4 failed: " + testsFailed);

        if (testsFailed > 0) {
            throw new IllegalStateException("Some Assignment 4 tests failed");
        }
    }

    private static void testUndirectedGraph() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addEdge("A", "B", 4.0);

        assertEquals("graph size", 2, graph.size());
        assertEquals("forward edge weight", 4.0, graph.getWeight("A", "B"));
        assertEquals("backward edge weight", 4.0, graph.getWeight("B", "A"));
    }

    private static void testBreadthFirstSearch() {
        WeightedGraph<String> graph = createGraph();
        BreadthFirstSearch<String> bfs = new BreadthFirstSearch<>(graph, "Almaty");

        assertTrue("bfs path exists", bfs.hasPathTo("Kyzylorda"));
        assertEquals("bfs path", "[Almaty, Taraz, Kyzylorda]", bfs.pathTo("Kyzylorda").toString());
    }

    private static void testDijkstraSearch() {
        WeightedGraph<String> graph = createGraph();
        DijkstraSearch<String> dijkstra = new DijkstraSearch<>(graph, "Almaty");

        assertTrue("dijkstra path exists", dijkstra.hasPathTo("Kyzylorda"));
        assertEquals("dijkstra path", "[Almaty, Shymkent, Kyzylorda]", dijkstra.pathTo("Kyzylorda").toString());
        assertEquals("dijkstra distance", 6.0, dijkstra.distTo("Kyzylorda"));
    }

    private static void testUnreachableVertex() {
        WeightedGraph<String> graph = createGraph();
        graph.addVertex("Atyrau");

        DijkstraSearch<String> dijkstra = new DijkstraSearch<>(graph, "Almaty");
        List<String> path = dijkstra.pathTo("Atyrau");

        assertTrue("unreachable has no path", !dijkstra.hasPathTo("Atyrau"));
        assertTrue("unreachable path empty", path.isEmpty());
        assertEquals("unreachable distance", Double.POSITIVE_INFINITY, dijkstra.distTo("Atyrau"));
    }

    private static WeightedGraph<String> createGraph() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addEdge("Almaty", "Astana", 7.0);
        graph.addEdge("Almaty", "Taraz", 2.0);
        graph.addEdge("Almaty", "Shymkent", 3.0);
        graph.addEdge("Taraz", "Kyzylorda", 10.0);
        graph.addEdge("Shymkent", "Kyzylorda", 3.0);
        graph.addEdge("Astana", "Kostanay", 4.0);
        return graph;
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            testsPassed++;
        } else {
            testsFailed++;
            System.out.println("FAIL: " + testName);
        }
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        boolean equal;
        if (expected == actual) {
            equal = true;
        } else if (expected == null || actual == null) {
            equal = false;
        } else {
            equal = expected.equals(actual);
        }

        if (equal) {
            testsPassed++;
        } else {
            testsFailed++;
            System.out.println("FAIL: " + testName + ". Expected " + expected + ", got " + actual);
        }
    }
}
