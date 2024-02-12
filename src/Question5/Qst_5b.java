package Question5;

import java.util.ArrayList;
import java.util.List;

public class Qst_5b {

    public static List<Integer> getImpactedDevices(int[][] edges, int targetDevice) {
        // Create an adjacency list representation of the network connections
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < edges.length + 1; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adjacencyList.get(edge[0]).add(edge[1]);
            adjacencyList.get(edge[1]).add(edge[0]);
        }

        // Initialize a boolean array to track visited devices during DFS
        boolean[] visited = new boolean[edges.length + 1];

        // Perform DFS to find impacted devices
        List<Integer> impactedDevices = new ArrayList<>();
        dfs(adjacencyList, visited, targetDevice, impactedDevices);

        return impactedDevices;
    }

    private static void dfs(List<List<Integer>> adjacencyList, boolean[] visited, int device, List<Integer> impactedDevices) {
        visited[device] = true;
        for (int neighbor : adjacencyList.get(device)) {
            if (!visited[neighbor]) {
                impactedDevices.add(neighbor);
                dfs(adjacencyList, visited, neighbor, impactedDevices);
            }
        }
    }

    public static void main(String[] args) {
        int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int targetDevice = 4;
        List<Integer> impactedDevices = getImpactedDevices(edges, targetDevice);
        System.out.println("Impacted Device List: " + impactedDevices);
    }
}
