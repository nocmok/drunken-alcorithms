import java.util.ArrayList;
import java.util.List;

public class Bridge {

    public static class IntPair {
        private int a;

        private int b;

        public IntPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        @Override
        public String toString() {
            return List.of(a, b).toString();
        }
    }

    public static class Clock {

        private int clock;

        public Clock() {
            this.clock = 0;
        }

        public int tik() {
            return clock++;
        }
    }

    public static void findBridges(List<List<Integer>> graph, int s, boolean[] used, Clock clock, int[] in, int[] min,
            int[] parent, List<IntPair> bridges) {

        if (used[s]) {
            return;
        }

        used[s] = true;
        min[s] = Integer.MAX_VALUE;
        in[s] = clock.tik();

        for (Integer next : graph.get(s)) {
            if (parent[s] == next) {
                continue;
            }
            if (used[next]) {
                min[s] = Integer.min(min[s], in[next]);
            } else {
                parent[next] = s;
                findBridges(graph, next, used, clock, in, min, parent, bridges);

                int newMin = Integer.min(in[next], min[next]);
                min[s] = Integer.min(min[s], newMin);

                // bridge found
                if (newMin > in[s]) {
                    bridges.add(new IntPair(s, next));
                }
            }
        }
    }

    public static List<IntPair> findBridges(List<List<Integer>> graph) {
        // min[i] - minimum in time of all reachable from i nodes, except i itself and

        int n = graph.size();
        Clock clock = new Clock();
        int[] parent = new int[n];
        int[] in = new int[n];
        int[] min = new int[n];
        boolean[] used = new boolean[n];
        List<IntPair> bridges = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            if (used[i]) {
                continue;
            }
            parent[i] = i;
            findBridges(graph, i, used, clock, in, min, parent, bridges);
        }

        return bridges;
    }
}
