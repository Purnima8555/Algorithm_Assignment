// 4a. You are given a 2D grid representing a maze in a virtual game world. The grid is of size m x n and consists
// of different types of cells:
//  'P' represents an empty path where you can move freely. 'W' represents a wall that you cannot pass through. 'S'
//  represents the starting point. Lowercase letters represent hidden keys. Uppercase letters represent locked doors.
//  You start at the starting point 'S' and can move in any of the four cardinal directions (up, down, left, right)
//  to adjacent cells. However, you cannot walk through walls ('W').
//  As you explore the maze, you may come across hidden keys represented by lowercase letters. To unlock a door
//  represented by an uppercase letter, you need to collect the corresponding key first. Once you have a key, you
//  can pass through the corresponding locked door.
//  For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the
//  English alphabet in the maze. This means that there is exactly one key for each door, and one door for each
//  key. The letters used to represent the keys and doors follow the English alphabet order.
//  Your task is to find the minimum number of moves required to collect all the keys. If it is impossible to
//  collect all the keys and reach the exit, return -1.
//  Example:
//  Input: grid = [ ["S","P","q","P","P"], ["W","W","W","P","W"], ["r","P","Q","P","R"]]
//  Output: 8
//  The goal is to Collect all key.


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question4;

// Algorithm Steps:
// 1.Initialize starting position, keys collected, and queue.
// 2.Start the BFS loop.
// 3.Poll current state from queue.
// 4.Check if all keys are collected, return steps if true.
// 5.Iterate over valid directions.
// 6.Explore neighboring cells, collect keys, and enqueue valid states.
// 7.Repeat until queue is empty or all keys are collected and return minimum moves.
// 8.If all keys cannot be collected, return -1.

import java.util.*;

public class Qst_4a {

    public static int minSteps(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int allKeys = 0;

        // Initializing variables
        int startX = -1, startY = -1;

        // To find start point and get total number of keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    allKeys |= (1 << (grid[i][j] - 'a'));
                }
            }
        }

        // Initializing BFS data structures
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new int[]{startX, startY, 0, 0}); // {x, y, keys, steps}
        visited.add(startX + "," + startY + ",0");

        // For directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1], keys = curr[2], steps = curr[3];

            if (keys == allKeys) // Check if all keys collected
                return steps;

            // To print position and no. of keys collected
            System.out.println("position (" + x + ", " + y + "), keys collected: " +keys);

            // Explore neighbouring cells
            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                // To check if neighbouring cells is within bounds and is not a wall
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') {
                    char c = grid[nx][ny];
                    int newKeys = keys;

                    if (Character.isLowerCase(c))
                        newKeys |= (1 << (c - 'a')); // Collect key if present

                    // check if door is there and the key is collected
                    if (Character.isUpperCase(c) && ((keys >> (c - 'A')) & 1) == 0)
                        continue; // If door locked and key not found

                    // Create new state and enqueue if not visited
                    String newState = nx + "," + ny + "," + newKeys;
                    if (!visited.contains(newState)) {
                        queue.offer(new int[]{nx, ny, newKeys, steps + 1});
                        visited.add(newState);
                    }
                }
            }
        }

        return -1;
        // Return if all keys weren't collected
    }

    public static void main(String[] args) {
        char[][] maze = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };
        System.out.println("Minimum number of moves required: " + minSteps(maze));
    }
}

// output: -1

// note:
// m = represents the number of rows in the maze grid.
// n = represents the number of columns in the maze grid.
// x, y = represents the current position (row and column) in the maze.
// i = represents index of current row
// j = represents index of current column within the row

// Steps Explained:
// 1. Create a Java class named `Qst_4a`
// 2. Define a method named `minSteps` within the `Qst_4a` class. This method will find the minimum number
//    of steps required to collect all keys in the maze.
// 3. Initialize variables to store the dimensions of the maze grid and to represent all keys.
// 4. Iterate through the maze grid to find the starting position ('S') and update the bitmask of all
//    keys found in the maze.
// 5. Utilize Breadth-First Search (BFS) to explore the maze and find the minimum moves to collect all keys.
// 6. Set up a queue and a set to perform BFS traversal.
// 7. Enqueue the starting position along with the collected keys and steps taken to start the BFS process.
// 8. Define the four possible movement directions: up, down, left, and right.
// 9. Explore adjacent cells by dequeuing positions from the queue, updating the collected keys, and
//     enqueuing new positions if they haven't been visited before.
// 10. Return the number of steps taken if all keys are collected, or return -1 if it's impossible to
//     collect all keys.
// 11. In the main method, create a sample maze grid, call the `minSteps` method to find the minimum number
//     of steps required to collect all keys, and print the result.