import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {

    public static int solve(int[][] g, boolean[] used, int team) {
        int n = g.length;
        if (team >= n) {
            return 0;
        }

        int with = 0;
        int without = 0;

        boolean canTake = true;
        for (int i = 0; i < n; ++i) {
            if (g[team][i] == 1 && used[i]) {
                canTake = false;
                break;
            }
        }

        if (canTake) {
            used[team] = true;
            with = 1 + solve(g, used, team + 1);
            used[team] = false;
        }

        without = solve(g, used, team + 1);

        return Integer.max(with, without);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            // participant name to teams
            Map<String, List<Integer>> map = new HashMap<>();
            List<List<String>> teams = new ArrayList<>();

            int[][] g = new int[n][n];
            for (int i = 0; i < n; ++i) {
                Arrays.fill(g[i], 0);
                g[i][i] = 1;
            }

            scanner.nextLine();

            for (int i = 0; i < n; ++i) {
                String[] names = scanner.nextLine().split("\\s+");
                teams.add(Arrays.asList(names));
                for (String name : names) {
                    List<Integer> ids = map.get(name);
                    if (ids == null) {
                        ids = new ArrayList<>();
                        map.put(name, ids);
                    }
                    ids.add(i);
                }
            }

            for (int i = 0; i < n; ++i) {
                for (String name : teams.get(i)) {
                    for (Integer team : map.get(name)) {
                        g[i][team] = 1;
                    }
                }
            }

            boolean[] used = new boolean[n];
            Arrays.fill(used, false);

            int result = solve(g, used, 0);
            System.out.println(result);
        }
    }
}