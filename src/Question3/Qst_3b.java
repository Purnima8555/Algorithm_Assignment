package Question3;

import java.util.*;

class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int u) {
        if (u != parent[u]) {
            parent[u] = find(parent[u]);
        }
        return parent[u];
    }

    void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
    }
}

public class Qst_3b {
    int vertices;
    List<Edge> edges;

    public Qst_3b(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    void addEdge(int source, int destination, int weight) {
        edges.add(new Edge(source, destination, weight));
    }

    void kruskalMST() {
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(edges);
        DisjointSet disjointSet = new DisjointSet(vertices);

        List<Edge> result = new ArrayList<>();

        while (!minHeap.isEmpty() && result.size() < vertices - 1) {
            Edge edge = minHeap.poll();
            int rootSource = disjointSet.find(edge.source);
            int rootDestination = disjointSet.find(edge.destination);

            if (rootSource != rootDestination) {
                result.add(edge);
                disjointSet.union(rootSource, rootDestination);
            }
        }

        // Print the minimum spanning tree
        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : result) {
            System.out.println(edge.source + " - " + edge.destination + ": " + edge.weight);
        }
    }

    public static void main(String[] args) {
        int vertices = 6;
        Qst_3b graph = new Qst_3b(vertices);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 6);

        graph.kruskalMST();
    }
}

