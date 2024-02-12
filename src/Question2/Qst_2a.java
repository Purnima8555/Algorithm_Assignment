package Question2;

public class Qst_2a {
    public static int minMovesToEqualize(int[] machines) {
        int totalDresses = 0;
        int n = machines.length;

        // Calculate the total number of dresses
        for (int dress : machines) {
            totalDresses += dress;
        }

        // If the total dresses cannot be evenly distributed, return -1
        if (totalDresses % n != 0) {
            return -1;
        }

        // Calculate the target number of dresses for each machine
        int targetDresses = totalDresses / n;
        int moves = 0;

        // Iterate through the machines and calculate the difference with the target
        int diff = 0;
        for (int i = 0; i < n - 1; i++) {
            diff += machines[i] - targetDresses;
            // If the current machine has more dresses than the target, move the excess to the next machine
            if (diff != 0) {
                moves++;
            }
        }
        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {1, 0, 5};
        int minMoves = minMovesToEqualize(machines);
        System.out.println("Minimum moves to equalize dresses: " + minMoves);
    }
}
