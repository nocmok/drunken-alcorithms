import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Solution {

    public static void dfs(List<List<Integer>> g, int v, List<Integer> circuit) {
        while (!g.get(v).isEmpty()) {
            int u = g.get(v).get(g.get(v).size() - 1);
            g.get(v).remove((int) (g.get(v).size() - 1));
            dfs(g, u, circuit);
        }
        circuit.add(v);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // number of circuits
            int c = scanner.nextInt();
            List<List<Integer>> g = new ArrayList<>(1001);
            for (int i = 0; i <= 1000; ++i) {
                g.add(new ArrayList<>());
            }

            // number of bus stops
            int n = 0;

            for (int i = 0; i < c; ++i) {
                int m = scanner.nextInt();

                // start bus station
                int prev = scanner.nextInt();
                n = Integer.max(n, prev);

                for (int j = 1; j <= m; ++j) {
                    int v = scanner.nextInt();
                    g.get(prev).add(v);
                    n = Integer.max(n, v);
                    prev = v;
                }

            }

            List<Integer> circuit = new ArrayList<>();
            dfs(g, 1, circuit);
            Collections.reverse(circuit);

            System.out.print(circuit.size() - 1);
            for (Integer v : circuit) {
                System.out.print(" " + v);
            }
            System.out.println();
        }
    }
}