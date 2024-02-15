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
package Question2;

// Algorithm Steps:
// 1.Iterate through the array of dress counts to calculate the total number of dresses.
// 2.Check if the dresses can be distributed evenly to each machine.
// 3.Calculate the target number of dresses for each machine by dividing the total dresses by the number of machines.
// 4.Iterate through machines to get the number of excess dresses compared to the target number of dresses.
// 5.Initialize the array with number of dresses in each machine, call the "minimumMoves" method and print the result.

public class Qst_2a {
    public static int minimumMoves(int[] machines) {
        int totalDresses = 0;
        int n = machines.length;

        // Calculate the total number of dresses
        for (int dress : machines) {
            totalDresses = totalDresses + dress;
        }

        // If the total dresses cannot be evenly distributed, return -1
        if (totalDresses % n != 0) {
            return -1;
        }

        int moves = getMoves(machines, totalDresses, n);
        return moves;
    }

    // To calculate target number of dresses for each machine
    private static int getMoves(int[] machines, int totalDresses, int n) {
        int targetDressAmount = totalDresses / n;
        int moves = 0;

        // Iterate through the machines and calculate the difference with the target
        int diff = 0;
        for (int i = 0; i < n - 1; i++) {
            diff = diff + (machines[i] - targetDressAmount);

            // If the current machine has more dresses than the target, move the excess to the next machine
            if (diff != 0) {
                moves++;
            }
        }
        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {1, 0, 5};
        int result = minimumMoves(machines);
        System.out.println("Minimum moves to equalize dresses: " + result);
    }
}

// output: 2

// note:
// n = total number of sewing machines
// i = index of current sewing machine in the loop

// Steps Explanation:
// 1. A java class is made and then a method is created to take an array of integers representing the number of
//    dresses in each sewing machine as input.
// 2. Initialize a variable "totalDresses" to store the total number of dresses and set it to 0.
// 3. Get the length of the input array "machines" and store it in a variable "n" by iterating through "machines"
//    array and calculating total number of dresses.
// 4. Check if the dresses can be distributed evenly.
// 5. Calculate the target number of dresses for each machine by dividing the total number of dresses by the number
//    of sewing machines "n".
// 6. Initialize a variable "moves" to store the minimum number of moves needed to equalize the dresses and set it to 0.
// 7. Initialize a variable "diff" to track the difference between the actual number of dresses and the target amount.
// 8. Iterate through the "machines" array from the first machine to the second-last machine (index i from 0 to
//    n - 2). It's needed because we're comparing the dress counts of adjacent machines to decide if a move is needed
//    to equalize them. If we went up to the last machine, we wouldn't have a next machine to compare with.
// 9. Update the "diff" variable by adding the difference between the number of dresses in the current machine and
//    the target dress amount.
// 10. If the "diff" is not zero (indicating that the current machine has more dresses than the target), increment
//     the "moves" variable.   Return the value of the "moves" variable, which represents the minimum number of moves needed to equalize
//     the dresses.
