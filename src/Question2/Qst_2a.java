package Question2;

public class Qst_2a {

    public static int minMovesToEqualize(int[] machines) {
        int totalDresses = 0;
        int numMachines = machines.length;

        // Step 1: Calculate the total number of dresses on the production line
        for (int dresses : machines) {
            totalDresses += dresses;
        }

        // Step 2: If the total number of dresses is not divisible by the number of machines, return -1
        if (totalDresses % numMachines != 0) {
            return -1;
        }

        // Step 3: Calculate the target number of dresses each machine should have
        int targetDresses = totalDresses / numMachines;

        // Step 4: Initialize variables
        int maxMoves = 0;
        int currentMoves = 0;
        int cumulativeDeficit = 0;

        // Step 5: Iterate through the machines and calculate the maximum moves needed
        for (int dresses : machines) {
            // Calculate the dresses deficit for the current machine
            int deficit = dresses - targetDresses;
            // Update the cumulative dresses deficit
            cumulativeDeficit += deficit;
            // Calculate the current moves
            currentMoves = Math.max(deficit, Math.abs(cumulativeDeficit));
            // Update the maximum moves needed
            maxMoves = Math.max(maxMoves, currentMoves);
        }

        // Step 6: Return the maximum moves needed
        return maxMoves;
    }

    public static void main(String[] args) {
        int[] machines = {2, 1, 3, 0, 2};
        System.out.println(minMovesToEqualize(machines)); // Output: 5
    }
}
