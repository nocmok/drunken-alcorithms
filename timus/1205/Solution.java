import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Solution {

    static class Pair<A, B> {

        A a;

        B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public static <A, B> Pair<A, B> of(A a, B b) {
            return new Pair<A, B>(a, b);
        }
    }

    public static void dijkstra(double[][] g, int s, double[] d, int[] prev) {
        int n = g.length;

        Arrays.fill(d, Double.POSITIVE_INFINITY);
        d[s] = 0d;

        boolean[] set = new boolean[n];
        Arrays.fill(set, false);

        for (int k = 0; k < n - 1; ++k) {

            int min = -1;

            for (int i = 0; i < n; ++i) {
                if (set[i]) {
                    continue;
                }
                if (min == -1 || d[min] > d[i]) {
                    min = i;
                }
            }

            if (Double.isInfinite(d[min])) {
                break;
            }

            for (int i = 0; i < n; ++i) {
                if (Double.isInfinite(g[min][i])) {
                    continue;
                }
                if (d[min] + g[min][i] < d[i]) {
                    d[i] = d[min] + g[min][i];
                    prev[i] = min;
                }
            }

            set[min] = true;
        }

    }

    public static double time(Pair<Double, Double> a, Pair<Double, Double> b, double velocity) {
        double dy = a.a - b.a;
        double dx = a.b - b.b;
        return Math.sqrt(dy * dy + dx * dx) / velocity;
    }

    public static Pair<Double, List<Integer>> solve(List<Pair<Double, Double>> points,
            List<Pair<Integer, Integer>> links, Pair<Double, Double> a, Pair<Double, Double> b, Double metroVelocity,
            Double humanVelocity) {

        int n = points.size();

        // let a, b, be graph nodes with indexes n, n + 1
        points.add(a);
        points.add(b);

        double[][] g = new double[n + 2][n + 2];
        for (int i = 0; i < n + 2; ++i) {
            Arrays.fill(g[i], Double.POSITIVE_INFINITY);
            g[i][i] = 0d;
        }

        for (int i = 0; i < links.size(); ++i) {
            Pair<Integer, Integer> link = links.get(i);
            g[link.a][link.b] = g[link.b][link.a] = time(points.get(link.a), points.get(link.b), metroVelocity);
        }

        for (int i = 0; i < n + 2; ++i) {
            for (int j = i + 1; j < n + 2; ++j) {
                if (Double.isInfinite(g[i][j])) {
                    g[i][j] = g[j][i] = time(points.get(i), points.get(j), humanVelocity);
                }
            }
        }

        double[] d = new double[n + 2];
        int[] prev = new int[n + 2];

        dijkstra(g, n, d, prev);

        LinkedList<Integer> path = new LinkedList<>();
        for (int node = prev[n + 1]; node != n; node = prev[node]) {
            path.addFirst(node);
        }

        return Pair.of(d[n + 1], path);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            double humanVelocity = scanner.nextDouble();
            double metroVelocity = scanner.nextDouble();
            int n = scanner.nextInt();

            List<Pair<Double, Double>> points = new ArrayList<>(n);

            for (int i = 0; i < n; ++i) {
                points.add(Pair.of(scanner.nextDouble(), scanner.nextDouble()));
            }

            List<Pair<Integer, Integer>> links = new ArrayList<>();

            for (;;) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                if (from == 0 && to == 0) {
                    break;
                }
                links.add(Pair.of(from - 1, to - 1));
            }

            Pair<Double, Double> a = Pair.of(scanner.nextDouble(), scanner.nextDouble());
            Pair<Double, Double> b = Pair.of(scanner.nextDouble(), scanner.nextDouble());

            Pair<Double, List<Integer>> ans = solve(points, links, a, b, metroVelocity, humanVelocity);

            System.out.printf("%.7f\n", ans.a);
            System.out.print(ans.b.size());
            for (int i = 0; i < ans.b.size(); ++i) {
                System.out.print(" " + (ans.b.get(i) + 1));
            }
            System.out.println();
        }
    }
}