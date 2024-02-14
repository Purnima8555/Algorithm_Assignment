//  1b. You are the captain of a spaceship, and you have been assigned a mission to explore a distant galaxy. Your
//  spaceship is equipped with a set of engines, where each engine represented by a block. Each engine requires a
//  specific amount of time to be built and can only be built by one engineer.
//  Your task is to determine the minimum time needed to build all the engines using the available engineers. The
//  engineers can either work on building an engine or split into two engineers, with each engineer sharing the
//  workload equally. Both decisions incur a time cost.
//  The time cost of splitting one engineer into two engineers is given as an integer split. Note that if two engineers
//  split at the same time, they split in parallel so the cost would be split.
//  Your goal is to calculate the minimum time needed to build all the engines, considering the time cost of splitting
//  engineers.
//  Input: engines= [1,2,3]
//  Split cost (k)=1
//  Output: 4


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question1;

// Algorithm Steps:
//  1.Calculate the minimum time considering both individual and split work scenarios.
//  2.Implement a method to calculate the minimum time for individual work. It iterates through the array of
//  engine building times and calculates the total time required by summing up the times for each engine.
//  3.Implement a method to calculate the minimum time for split work. It calculates the total time required to
//  build all engines with splitting by summing up the times for each engine and adding the split cost for each split.
//  4.Compare the minimum times from both scenarios and return the minimum time.

public class Qst_1b {

    public static int minTimeToBuildEngines(int[] engines, int splitCost) {

        // Recursive function to find the minimum time for both scenarios
        int individualTime = minTimeToBuildIndividually(engines);
        int splitTime = minTimeToBuildSplitting(engines, splitCost);

        // Return the minimum time among both scenarios
        return Math.min(individualTime, splitTime);
    }

    // Calculate the minimum time when working individually
    private static int minTimeToBuildIndividually(int[] engines) {
        int totalTime = 0;
        for (int i = 0; i < engines.length; i++) {
            totalTime = totalTime + engines[i];
        }
        return totalTime;
    }

    // Calculate the minimum time when splitting their work
    private static int minTimeToBuildSplitting(int[] engines, int splitCost) {
        int totalEngines = engines.length;
        if (totalEngines == 0) return 0;
        int totalTime = 0;
        for (int i = 0; i < engines.length; i++) {
            totalTime = totalTime + engines[i];
        }
        totalTime = totalTime + (totalEngines - 1) * splitCost;
        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {1, 2, 3};
        int splitCost = 1;
        int minTime = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time to build all engines: " + minTime);
    }
}

// output: 6

// note:
// i = index of current engine in the loop

// Steps Explanation:
// 1. A java class is made and then a method is created.
// 2. Then, a method is defined to calculate the minimum time to build engines, taking two parameters: an array of
//    engine building times and the split cost.
// 3. Recursive function to find the minimum time for both scenarios is called and the minimum time among both
//    scenarios using the Math.min function is returned.
// 4. The "minTimeToBuildIndividually" method calculates the minimum time required when engineers work individually on
//    each engine. It iterates through the engines array and accumulates the total time required.
// 5. The minTimeToBuildEnginesSplit method calculates the minimum time required when engineers split their work. It
//    also iterates through the engines array to calculate the total time required to build all engines and adds the
//    split cost for splitting engineers.
// 6. Now, in main method, the program starts by calculating the minimum time to build all engines using the
//    "minTimeToBuildEngines" method and prints the minimum time to build all engines.
