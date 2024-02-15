//  2b. You are given an integer n representing the total number of individuals. Each individual is identified by a unique
//  ID from 0 to n-1. The individuals have a unique secret that they can share with others.
//  The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret
//  with any number of individuals simultaneously during specific time intervals. Each time interval is represented by
//  a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
//  You need to determine the set of individuals who will eventually know the secret after all the possible secret
//  sharing intervals have occurred.
//  Example:
//  Input: n = 5, intervals = [(0, 2), (1, 3), (2, 4)], firstPerson = 0
//  Output: [0, 1, 2, 3, 4]


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question2;

// Algorithm Steps:
// 1.Initialize a boolean array to know the individuals to knows secret, making the first index true.
// 2.Iterate through each interval to make the corresponding position in the array as true.
// 3.Collect individuals marked as true in array knowsSecret into a list secretKeepers.
// 4.Return the secretKeepers list containing the position of people who know the secret.
// 5.In the main method, initialize variables and create an instance of the sharingSecret class.
// 6.Call the findKnownSecret method and print the list of individuals who know the secret.

import java.util.ArrayList;
import java.util.List;

public class Qst_2b {

    public List<Integer> findKnownSecret(int n, int[][] intervals, int firstIndividual) {
        boolean[] secretKnower = new boolean[n];

        // First person knows secret so boolean value is true
        secretKnower[firstIndividual] = true;

        // Iterate through intervals
        for (int j = 0; j < intervals.length; j++) {
            int[] interval = intervals[j];
            int start = interval[0];
            int end = interval[1];

        // Sharing the secret within the current interval to the current individual
            for (int i = start; i <= end; i++) {
                if (!secretKnower[i]) {
                    secretKnower[i] = true;
                }
            }
        }

        // Individuals whom the secret have been told
        List<Integer> secretKeepers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (secretKnower[i]) {
                secretKeepers.add(i);
            }
        }

        return secretKeepers;
    }

    public static void main(String[] args) {
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int n = 5;
        int firstIndividual = 0;
        Qst_2b sharingSecret = new Qst_2b();
        List<Integer> knownSecret = sharingSecret.findKnownSecret(n, intervals, firstIndividual);

        System.out.println("Individuals who know the secret: " + knownSecret);
    }
}

// output: [0, 1, 2, 3, 4]

// note:
// n = total number of individuals
// j = index of current interval in the loop
// i = index of current individual in the loop

// Steps Explanation:
// 1. Define a Java class and create a method "findKnownSecret" to determine individuals who know the secret
//    after all sharing intervals.
// 2. Initialize a boolean array "secretKnower" to track individuals who know the secret.
// 3. Set the initial state where only the first person knows the secret by setting first index to true.
// 4. Iterate through each sharing interval in the "intervals" array.
// 5. Within each interval, update "secretKnower" to include individuals who receive the secret.
// 6. Iterate through each individual and set their corresponding index in "secretKnower" to true if they receive the secret.
// 7. Create an ArrayList named "secretKeepers" to store index of individuals who know the secret.
// 8. Iterate through "secretKnower" and add individuals who know the secret to "secretKeepers".    Return the list of individuals who know the secret.
