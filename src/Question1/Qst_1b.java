package Question1;

public class Qst_1b {

    public static int minTime(int[] engines, int splitCost) {
        int totalTime = 0;
        int maxTime = 0;

        // Step 1: Calculate the total time required without splitting engineers
        for (int time : engines) {
            totalTime += time;
            maxTime = Math.max(maxTime, time);
        }

        // Step 2: Calculate the number of splits required
        int numSplits;
        if (engines.length > 1) {
            numSplits = engines.length - 1;
        } else {
            numSplits = 0;
        }

        // Step 3: Calculate the total time considering splits
        int remainingMaxTime = maxTime; // Initialize remainingMaxTime
        for (int i = 1; i < engines.length; i++) {
            remainingMaxTime = Math.max(remainingMaxTime, engines[i]);
        }
        int splitTime = remainingMaxTime + splitCost; // Time for split

        // Return the minimum of totalTime and splitTime
        return Math.min(totalTime, splitTime + numSplits * splitCost);
    }

    public static void main(String[] args) {
        int[] engines = {3, 4, 5, 2};
        int splitCost = 2;
        System.out.println(minTime(engines, splitCost));
    }
}

// output:4