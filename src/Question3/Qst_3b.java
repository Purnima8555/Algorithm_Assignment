// 3b. Implement Kruskal algorithm and priority queue using minimum heap.


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question3;

// Algorithm Steps:
// 1.Initialize necessary data structures and variables.
// 2.Create a priority queue to store edges sorted by weight.
// 3.Add all edges to the priority queue.
// 4.Iterate until v - 1 edges are taken or the priority queue is empty:
// 5.Poll the edge with the smallest weight.
// 6.Check if adding the edge creates a cycle using union-find.
// 7.If no cycle is detected, add the edge to the minimum spanning tree.
// 8.Traverse the minimum spanning tree and print the edges with their weights.

import java.util.*;

public class Qst_3b {

    public static class Edge {
        int s;
        int d;
        int w;

        // Constructor to initialize the edge
        Edge(int s, int d, int w) {
            this.s = s;
            this.d = d;
            this.w = w;
        }
    }

    int v;
    int graph[][]; // Adjacency matrix to represent the graph
    ArrayList<Edge> edges;

    // Constructor to initialize the graph with a given number of vertices
    Qst_3b(int v){
        this.v = v;
        graph = new int[v][v];
        edges = new ArrayList<>();
    }

    // Method to add an edge to the graph
    void addEdge(int s, int d, int w){
        graph[s][d] = w;
        graph[d][s] = w;
        edges.add(new Edge(s, d, w));
    }

    // Method to find the minimum spanning tree using kruskal alg
    void mst() {
        int parent[] = new int[v];
        for (int i = 0; i < v; i++) {
            parent[i] = -1;
        }
        // Initialize the MST graph
        int mstgraph[][] = new int[v][v];

        // Priority queue to store edges sorted by weight
        PriorityQueue<Edge> pQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.w));
        pQueue.addAll(edges);

        int edgeTaken = 0;

        // Loop until v-1 edges are added to the MST or priority queue is empty
        while (edgeTaken < v - 1 && !pQueue.isEmpty()) {
            Edge e = pQueue.poll();
            if (!isCycleDetected(e.s, e.d, parent)) {  // Check if adding this edge forms a cycle
                mstgraph[e.s][e.d] = e.w;
                mstgraph[e.d][e.s] = e.w;
                edgeTaken++;
                parent[e.d] = e.s; // Update parent array to track connected components
            }
        }

        // Print the minimum spanning tree
        System.out.println("Minimum Spanning Tree:");
        for (int i = 0; i < v; i++) {
            for (int j = i + 1; j < v; j++) {
                if (mstgraph[i][j] != 0) {
                    System.out.println(i + " - " + j + ": " + mstgraph[i][j]);
                }
            }
        }
    }

    // Method to check if adding an edge forms a cycle in the MST
    boolean isCycleDetected(int u, int v, int[] parent){
        return find(u, parent) == find(v, parent);
    }

    // Method to find the parent of a vertex
    int find(int vertex, int[] parent){
        if (parent[vertex] == -1) {
            return vertex;
        }
        return find(parent[vertex], parent);
    }

    public static void main(String[] args) {
        Qst_3b adj = new Qst_3b(5);
        adj.addEdge(0, 1, 4);
        adj.addEdge(0, 2, 2);
        adj.addEdge(1, 3, 3);
        adj.addEdge(2, 4, 5);
        adj.addEdge(3, 4, 1);

        adj.mst();  // Call the MST method to find the minimum spanning tree
    }
}

// note:
// s = source vertex of an edge
// d = destination vertex of an edge
// w = weight of an edge
// e = an edge in the graph
// v = number of vertices in the graph
// u = a vertex or node in the graph
// i = a row index or a loop variable used for iteration
// j = a column index or a loop variable used for iteration

// Steps Explanation:
// 1. Initialize data structures to store the graph, edges, parent vertices, and edge weights.
// 2. Create a priority queue (min heap) to store edges sorted by weight.
// 3. Iterate over all edges and add each edge to the priority queue.
// 5. Initialize an array to represent the parent of each vertex in the forest. Initially, each vertex is its own parent (-1).
// 6. Initialize a count to keep track of the number of edges taken in the minimum spanning tree.
// 7. Repeat until v - 1 edges have been added to the minimum spanning tree or the priority queue is empty:
//      * Poll(remove and return) the edge with the smallest weight from the priority queue.
//      * Check if adding this edge creates a cycle using the union-find algorithm:
//          * Find the parent of the start vertex (s) and the destination vertex (d).
//          * If the parents are different, merge the sets by updating the parent of one vertex to the other vertex's
//          parent. Otherwise, skip this edge.
// 8. If the edge doesn't create a cycle, add it to the minimum spanning tree and increment the count of edges taken.
// 9. Traverse the constructed minimum spanning tree graph and print the edges along with their weights.
