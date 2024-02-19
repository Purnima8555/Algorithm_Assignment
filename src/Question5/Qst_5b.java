// 5b.Assume you were hired to create an application for an ISP, and there are n network devices, such as routers,
//  that are linked together to provide internet access to users. You are given a 2D array that represents network
//  connections between these network devices. write an algorithm to return impacted network devices, If there is
//  a power outage on a certain device, these impacted device list assist you notify linked consumers that there is a
//  power outage and it will take some time to rectify an issue.
//  Input: edges= {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}}
//  Target Device (On which power Failure occurred): 4
//  Output (Impacted Device List) = {5,7}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question5;

// Algorithm Steps:
// 1.Create an adjacency list to represent network connections.
// 2.Traverse the network connections using DFS.
// 3.Mark visited devices to avoid revisiting.
// 4.For each unvisited neighbor, add it to the list of impacted devices and recursively explore its neighbors.
// 5.Return the list of impacted devices.

import java.util.ArrayList;
import java.util.List;

public class Qst_5b {

    public static List<Integer> getImpactedDevices(int[][] edges, int targetDevice) {
        // Create an adjacency list representing network connections
        List<List<Integer>> adjacencyList = new ArrayList<>();

        // Iterate to initialize adjacency list with empty lists
        for (int i = 0; i < edges.length + 1; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        // Iterate adjacency list with edges
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
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
        // Loop through neighbors of the current device
        for (int neighbor : adjacencyList.get(device)) {

            // Check if the neighbor device is not visited
            if (!visited[neighbor]) {
                impactedDevices.add(neighbor);   // If not visited, add it to impactedDevices and recursively call DFS
                dfs(adjacencyList, visited, neighbor, impactedDevices);
            }
        }
    }

    public static void main(String[] args) {
        // network connections and target device
        int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int targetDevice = 4;
        List<Integer> impactedDevices = getImpactedDevices(edges, targetDevice);
        System.out.println("Impacted Device List: " + impactedDevices);
    }
}

// output: [2, 0, 1, 3, 6, 5, 7]

// note:
// i = represents the index of the current edge in the loop

// Steps Explained:
// 1. Define the Qst_5b class.
// 2. Define the getImpactedDevices method, which takes a 2D array edges representing network connections and an integer
//    targetDevice. It returns a list of integers representing impacted devices.
// 3. Declare a list named adjacencyList to represent the adjacency list of the network connections.
// 4. Initialize the adjacencyList with empty lists for each device in the network. For each device in the network, we
//    add an empty list to the adjacency list. This ensures that every device has a corresponding list to store its
//    neighboring devices.
// 5. Populate the adjacencyList based on the given edges. We iterate through each edge and establish bidirectional
//    connections between the devices it connects in the adjacency list, ensuring each device knows its neighbors.
// 6. Declare a boolean array named visited to keep track of visited devices during depth-first search (DFS).
// 7. Initialize an empty list impactedDevices to store the impacted devices found during DFS. Initiate the DFS by calling
//    the dfs method with the adjacency list, visited array, target device, and impacted devices list as parameters.
// 8. Return the list of impacted devices after DFS traversal.