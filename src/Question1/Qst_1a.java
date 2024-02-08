// to find the minimum cost of decorating venues with different themes:

package Question1;

public class Qst_1a {

    public static int minDecorCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        // Initialize another array to store minimum cost
        int[][] minCosts = new int[n][k];

        // Initialize the first row of minCosts with the costs of the first venue
        for (int i = 0; i < k; i++) {
            minCosts[0][i] = costs[0][i];
        }

        // Iterate through venues starting from the index 1
        for (int i = 1; i < n; i++) {
            // Iterate through themes
            for (int j = 0; j < k; j++) {
                // Initialize the minimum cost for the current theme as infinity
                int minDecorCost = Integer.MAX_VALUE;
                // Iterate through themes for the previous venue
                for (int t = 0; t < k; t++) {
                    if (t != j) {
                        int cost = costs[i][j] + minCosts[i - 1][t];
                        minDecorCost = Math.min(minDecorCost, cost);
                    }
                }
                // Update the minCosts array with the minimum cost for the current venue and theme
                minCosts[i][j] = minDecorCost;
            }
        }

        // Iterate to find the minimum cost of decorating all venues
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            minCost = Math.min(minCost, minCosts[n - 1][i]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costArray = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int result = minDecorCost(costArray);
        System.out.println("Minimum Cost:"+ result);
    }
}

// output:7

// note:
// n = number of venues in the "costs" array
// k = number of themes in the "costs" array
// i = index of current venue in the loop
// j = index of current theme in the loop
// t = represents theme that is taken/used in the adjacent venue

// Explanation:
// 1. A java class is made and then a method is created.
// 2. The input array 'cost' is checked as it shouldn't be null or empty and should have at least one row.
// 3. Variables "n" and "k" are initialized ro represent number of venues and themes respectively.
// 4. A 2D array "minCosts" is created to store the minimum costs.
// 5. The first row of "minCosts" is initialized with the costs of decorating the first venue with each theme as there
//    is no previous venues to consider.
// 6. Now, nested for loop is used to iterate over the venues and themes.
// 7. Firstly, we iterate through venues and start from index 1 instead of 0 because we need to consider the costs of
//    decorating each venue based on the previous venue's theme.
// 8. Then, we iterate through available themes for the current venue.
// 9. Now, we initialize the minimum cost for current theme with maximum possible integer to ensure it gets updated with
//    actual cost later.
// 10. Again, we iterate through themes, this time, to consider the cost of each theme with previous venue(t). We need
//     to consider the cost of decorating the previous venue with all themes except the current theme(j).
// 11. The cost of current venue with current theme is calculated and the method "minDecorCost" is updated.
// 12. The "minCosts" array is also updated with the minimum cost for the current venue and theme.
// 13. After iterating through all venues, we need to find the minimum cost of decorating the last venue. This is
//     achieved by examining the costs in the last row of the "minCosts" array.
// 14. Finally, we return the minimum cost.