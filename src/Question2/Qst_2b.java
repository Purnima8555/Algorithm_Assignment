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
// Algorithm Steps:

package Question2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Qst_2b {

    public static List<Integer> findKnownSecret(int n, int[][] intervals, int firstPerson) {
        // Step 1: Initialize a list to track received intervals for each person
        List<List<int[]>> received = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            received.add(new ArrayList<>());
        }

        // Step 2: Iterate through intervals and update received intervals
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            for (int i = start; i <= end; i++) {
                received.get(i).add(interval);
            }
        }

        // Step 3: Initialize a set to store IDs of individuals who know the secret
        Set<Integer> known = new HashSet<>();

        // Step 4: DFS to find all individuals who eventually know the secret
        dfs(firstPerson, received, known);

        // Step 5: Return the sorted list of IDs in the known set
        List<Integer> result = new ArrayList<>(known);
        result.sort(null);
        return result;
    }

    private static void dfs(int person, List<List<int[]>> received, Set<Integer> known) {
        if (known.contains(person)) {
            return;
        }
        known.add(person);
        for (int[] interval : received.get(person)) {
            int start = interval[0];
            int end = interval[1];
            for (int i = start; i <= end; i++) {
                dfs(i, received, known);
            }
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;
        List<Integer> known = findKnownSecret(n, intervals, firstPerson);
        System.out.println(known); // Output: [0, 1, 2, 3, 4]
    }
}

// output: [0, 1, 2, 3, 4]

// note:
//

// Steps Explanation: