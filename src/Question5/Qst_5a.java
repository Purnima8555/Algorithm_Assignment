//package Question5;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Qst_5a {
//}
//package Question5;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class Qst_5a {
//
//    public static List<Integer> findImpactedDevices(int[][] connections, int affectedDevice) {
//        int n = connections.length;
//        boolean[] visited = new boolean[n];
//        List<Integer> impactedDevices = new ArrayList<>();
//
//        dfs(connections, affectedDevice, visited, impactedDevices);
//
//        return impactedDevices;
//    }
//
//    private static void dfs(int[][] connections, int device, boolean[] visited, List<Integer> impactedDevices) {
//        visited[device] = true;
//        impactedDevices.add(device);
//
//        for (int neighbor = 0; neighbor < connections.length; neighbor++) {
//            if (connections[device][neighbor] == 1 && !visited[neighbor]) {
//                dfs(connections, neighbor, visited, impactedDevices);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        int[][] connections = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
//        int affectedDevice = 4;
//        List<Integer> impactedDevices = findImpactedDevices(connections, affectedDevice);
//        System.out.println("Impacted devices: " + impactedDevices);
//    }
//}
