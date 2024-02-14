package Question4;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class State {
    int x, y;
    int keys;
    int steps;

    public State(int x, int y, int keys, int steps) {
        this.x = x;
        this.y = y;
        this.keys = keys;
        this.steps = steps;
    }
}

public class Qst_4a {
    public static int shortestPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int targetKeys = 0;
        int startX = 0, startY = 0;

        // Find the starting position and count the number of target keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    targetKeys++;
                }
            }
        }

        Queue<State> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new State(startX, startY, 0, 0));
        visited.add(startX + "-" + startY + "-0");

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int collectedKeys = 0;

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            if (collectedKeys == targetKeys) {
                return curr.steps;
            }

            for (int[] dir : directions) {
                int newX = curr.x + dir[0];
                int newY = curr.y + dir[1];

                if (newX < 0 || newX >= m || newY < 0 || newY >= n || grid[newX][newY] == 'W') {
                    continue; // Cannot move to this cell
                }

                char cell = grid[newX][newY];
                int newKeys = curr.keys;

                if (Character.isUpperCase(cell)) {
                    // Check if the key for this door is collected
                    if ((curr.keys & (1 << (cell - 'A'))) == 0) {
                        continue; // Door is locked and key not collected
                    }
                } else if (Character.isLowerCase(cell)) {
                    // Collect the key
                    newKeys |= (1 << (cell - 'a'));
                    collectedKeys++;
                }

                String newStateKey = newX + "-" + newY + "-" + newKeys;
                if (!visited.contains(newStateKey)) {
                    visited.add(newStateKey);
                    queue.offer(new State(newX, newY, newKeys, curr.steps + 1));
                }
            }
        }

        return -1; // Impossible to collect all keys and reach the exit
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        System.out.println("Minimum number of moves: " + shortestPath(grid));
    }
}
