import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static int dfs(int[][] grid, int x, int y, boolean[][] visited) {
        if (visited[y][x] || grid[y][x] == 1) {
            return 0;
        }

        visited[y][x] = true;
        int n = grid.length;
        int result = 0;

        for (int dy = -1; dy <= 1; ++dy) {
            for (int dx = -1; dx <= 1; ++dx) {
                if (dx != 0 && dy != 0) {
                    continue;
                }
                int j = dx + x;
                int i = dy + y;
                if (i < 0 || i >= n || j < 0 || j >= n || grid[i][j] == 1) {
                    result += 1;
                } else if (!visited[i][j]) {
                    result += dfs(grid, j, i, visited);
                }
            }
        }

        return result;
    }

    public static int solve(int[][] g) {
        int n = g.length;
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(visited[i], false);
        }
        int result = dfs(g, 0, 0, visited) + dfs(g, n - 1, n - 1, visited);
        return (result - 4) * 9;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            // 1 - wall, 0 - empy space
            int[][] grid = new int[n][n];

            scanner.nextLine();
            for (int i = 0; i < n; ++i) {
                String line = scanner.nextLine();
                for (int j = 0; j < line.length(); ++j) {
                    char c = line.charAt(j);
                    grid[i][j] = ((c == '#') ? 1 : 0);
                }
            }

            int result = solve(grid);
            System.out.println(result);
        }
    }
}