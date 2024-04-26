import java.util.*;

class Graph {
    private final int vertices;
    private final List<List<Edge>> adjList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int dest, int weight) {
        adjList.get(source).add(new Edge(source, dest, weight));
    }

    public void dijkstra(int startVertex) {
        int[] distances = new int[vertices];
        boolean[] visited = new boolean[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(startVertex, startVertex, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.dest;
            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : adjList.get(u)) {
                int v = edge.dest;
                int weight = edge.weight;
                if (distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    pq.add(new Edge(u, v, distances[v]));
                }
            }
        }

        printSolution(distances, startVertex);
    }

    public void bellmanFord(int startVertex) {
        int[] distances = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            for (List<Edge> edges : adjList) {
                for (Edge edge : edges) {
                    int u = edge.source;
                    int v = edge.dest;
                    int weight = edge.weight;
                    if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                        distances[v] = distances[u] + weight;
                    }
                }
            }
        }

        for (List<Edge> edges : adjList) {
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.dest;
                int weight = edge.weight;
                if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                    System.out.println("Graph contains a negative-weight cycle");
                    return;
                }
            }
        }

        printSolution(distances, startVertex);
    }

    private void printSolution(int[] distances, int startVertex) {
        System.out.println("Vertex Distance from Source " + startVertex);
        for (int i = 0; i < vertices; i++) {
            System.out.println(i + " \t\t " + (distances[i] == Integer.MAX_VALUE ? "Infinity" : distances[i]));
        }
    }
}

class Edge {
    int source;
    int dest;
    int weight;

    public Edge(int source, int dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        int vertices = scanner.nextInt();

        Graph graph = new Graph(vertices);
        System.out.println("Enter the number of edges:");
        int edges = scanner.nextInt();

        System.out.println("Enter " + edges + " edges in the format <source> <destination> <weight>:");
        for (int i = 0; i < edges; i++) {
            int source = scanner.nextInt();
            int dest = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(source, dest, weight);
        }

        System.out.println("Enter the starting vertex:");
        int startVertex = scanner.nextInt();

        System.out.println("Choose the algorithm (1 = Dijkstra, 2 = Bellman-Ford):");
        int algorithm = scanner.nextInt();

        if (algorithm == 1) {
            graph.dijkstra(startVertex);
        } else if (algorithm == 2) {
            graph.bellmanFord(startVertex);
        } else {
            System.out.println("Invalid choice");
        }
    }
}
