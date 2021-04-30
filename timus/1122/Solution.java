import java.util.Scanner;

public class Solution {

    private static Scanner scanner = new Scanner(System.in);

    public static int[][] readGrid(int n) {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            for (int j = 0; j < n; ++j) {
                switch (line.charAt(j)) {
                case 'W':
                    grid[i][j] = 0;
                    break;
                case 'B':
                    grid[i][j] = 1;
                    break;
                }
            }
        }
        return grid;
    }

    public static int[][] readCombination(int n) {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            for (int j = 0; j < n; ++j) {
                switch (line.charAt(j)) {
                case '0':
                    grid[i][j] = 0;
                    break;
                case '1':
                    grid[i][j] = 1;
                    break;
                }
            }
        }
        return grid;
    }

    public static void applyCombination(int[][] grid, int[][] comb, int y, int x, int[][] result) {
        int n = result.length;
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (x + dx < 0 || x + dx > n - 1 || y + dy < 0 || y + dy > n - 1) {
                    continue;
                }
                result[y + dy][x + dx] = grid[y + dy][x + dx] ^ comb[dy + 1][dx + 1];
            }
        }
    }

    public static void applyAll(int[][] grid, int[][] comb, boolean[][] apply, int[][] result) {
        int n = result.length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (apply[i][j]) {
                    applyCombination(grid, comb, i, j, result);
                }
            }
        }
    }

    public static boolean allEquals(int[][] array) {
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j) {
                if (array[i][j] != array[0][0]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean allSubsets(boolean[][] used, int k, int[][] comb, int[][] grid) {
        if (k <= 0) {
            applyAll(grid, comb, used, grid);
            boolean success = allEquals(grid);
            if (!success) {
                // recovers initial state of grid
                applyAll(grid, comb, used, grid);
            }
            return success;
        }

        int n = used.length;

        for (int y = n - 1; y >= 0; --y) {
            for (int x = n - 1; x >= 0; --x) {
                if (used[y][x]) {
                    return false;
                }
                used[y][x] = true;
                if (allSubsets(used, k - 1, comb, grid)) {
                    return true;
                }
                used[y][x] = false;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int n = 4;

        int[][] grid = readGrid(n);
        int[][] comb = readCombination(3);

        boolean[][] used = new boolean[n][n];
        for (int k = 0; k <= n * n; ++k) {
            if (allSubsets(used, k, comb, grid)) {
                System.out.println(k);
                System.exit(0);
            }
        }

        System.out.println("Impossible");
    }
}