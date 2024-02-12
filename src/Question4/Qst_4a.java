package Question4;

import java.util.*;

public class Qst_4a {

    static class State {
        int row, col;
        int keys;

        public State(int row, int col, int keys) {
            this.row = row;
            this.col = col;
            this.keys = keys;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, keys);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof State)) return false;
            State other = (State) obj;
            return row == other.row && col == other.col && keys == other.keys;
        }
    }

    public static int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int keysToCollect = 0;
        int startRow = -1, startCol = -1;

        // Find the starting position and count the total number of keys to collect
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    keysToCollect++;
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.offer(new State(startRow, startCol, 0));
        visited.add(new State(startRow, startCol, 0));
        int moves = 0;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                State currentState = queue.poll();
                int row = currentState.row;
                int col = currentState.col;
                int keys = currentState.keys;

                if (keys == (1 << keysToCollect) - 1) {
                    return moves;
                }

                for (int[] dir : dirs) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];

                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && grid[newRow][newCol] != 'W') {
                        char cell = grid[newRow][newCol];
                        if (Character.isLowerCase(cell)) {
                            int newKeys = keys | (1 << (cell - 'a'));
                            State nextState = new State(newRow, newCol, newKeys);
                            if (!visited.contains(nextState)) {
                                queue.offer(nextState);
                                visited.add(nextState);
                            }
                        } else if (Character.isUpperCase(cell) && ((keys >> (cell - 'A')) & 1) == 1) {
                            State nextState = new State(newRow, newCol, keys);
                            if (!visited.contains(nextState)) {
                                queue.offer(nextState);
                                visited.add(nextState);
                            }
                        } else if (cell == 'P' || cell == 'S') {
                            State nextState = new State(newRow, newCol, keys);
                            if (!visited.contains(nextState)) {
                                queue.offer(nextState);
                                visited.add(nextState);
                            }
                        }
                    }
                }
            }
            moves++;
        }

        return -1;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };
        int minMoves = minMovesToCollectKeys(grid);
        System.out.println("Minimum moves to collect all keys: " + minMoves); // Output: 8
    }
}
