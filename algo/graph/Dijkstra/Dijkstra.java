import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

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

    /**
     * 
     * @param g - graph
     * @param s - index of start node
     * @param d - distances
     */
    public static void dijkstra(List<List<Pair<Integer, Double>>> g, int s, double[] d, int[] prev) {
        int v = g.size();
        Arrays.fill(d, Double.POSITIVE_INFINITY);
        d[s] = 0d;
        prev[s] = -1;

        boolean[] set = new boolean[v];
        Arrays.fill(set, false);

        for (int k = 0; k < v - 1; ++k) {

            int min = -1;

            // get node with min distance
            for (int i = 0; i < v; ++i) {
                if (set[i]) {
                    continue;
                }
                if (min == -1) {
                    min = i;
                } else if (d[i] < d[min]) {
                    min = i;
                }
            }

            if (Double.isInfinite(d[min])) {
                break;
            }

            // relaxation
            for (Pair<Integer, Double> link : g.get(min)) {
                if (d[min] + link.b < d[link.a]) {
                    d[link.a] = d[min] + link.b;
                    prev[link.a] = min;
                }
            }

            set[min] = true;
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
}