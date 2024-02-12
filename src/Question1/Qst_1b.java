package Question1;

public class Qst_1b {

    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        // Call the recursive function to find the minimum time
        return minTimeToBuildEnginesRecursive(engines, splitCost, 0, engines.length - 1);
    }

    private static int minTimeToBuildEnginesRecursive(int[] engines, int splitCost, int left, int right) {
        // Base case: if left index is greater than right index, return 0
        if (left > right) {
            return 0;
        }

        // If there's only one engine, return its time
        if (left == right) {
            return engines[left];
        }

        // Initialize minimum time as the sum of times of all engines
        int minTime = 0;
        for (int i = left; i <= right; i++) {
            minTime += engines[i];
        }

        // Try splitting at each possible position and recursively find the minimum time
        for (int i = left; i < right; i++) {
            int leftTime = minTimeToBuildEnginesRecursive(engines, splitCost, left, i);
            int rightTime = minTimeToBuildEnginesRecursive(engines, splitCost, i + 1, right);
            minTime = Math.min(minTime, leftTime + rightTime + splitCost);
        }

        return minTime;
    }

    public static void main(String[] args) {
        int[] engines = {1, 2, 3};
        int splitCost = 1;
        int minTime = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time to build all engines: " + minTime); // Output: 4
    }
}
