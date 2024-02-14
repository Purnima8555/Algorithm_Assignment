//  2a. You are the manager of a clothing manufacturing factory with a production line of super sewing machines. The
//  production line consists of n super sewing machines placed in a line. Initially, each sewing machine has a certain
//  number of dresses or is empty.
//  For each move, you can select any m (1 <= m <= n) consecutive sewing machines on the production line and pass
//  one dress from each selected sewing machine to its adjacent sewing machine simultaneously.
//  Your goal is to equalize the number of dresses in all the sewing machines on the production line. You need to
//  determine the minimum number of moves required to achieve this goal. If it is not possible to equalize the number
//  of dresses, return -1.
//  Input: [1,0,5]
//  Output: 2


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Algorithm Steps:

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

// output: 2

// note:
//

// Steps Explanation: