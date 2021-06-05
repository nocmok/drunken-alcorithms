import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.TreeSet;
import java.util.Arrays;

public class Prim {

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

    /**
     * 
     * @param graph i, j - distance bw i j, or Integer.MAX_VALUE if there is no link
     * @return
     */
    public static List<List<IntPair>> densePrim(int[][] graph) {
        int n = graph.length;

        boolean[] used = new boolean[n];
        int[] distance = new int[n];
        int[] parent = new int[n];

        Arrays.fill(distance, Integer.MAX_VALUE);

        int s = 0;
        used[s] = true;

        for (int j = 0; j < n; ++j) {
            if (graph[s][j] == Integer.MAX_VALUE) {
                continue;
            }
            distance[j] = graph[s][j];
            parent[j] = s;
        }

        List<List<IntPair>> mst = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            mst.add(new ArrayList<>());
        }

        for (int i = 1; i < n; ++i) {
            int min = -1;
            int bestDistance = Integer.MAX_VALUE;
            for (int j = 1; j < n; ++j) {
                if (used[j]) {
                    continue;
                }
                if (bestDistance > distance[j]) {
                    min = j;
                }
            }
            if (min == -1) {
                return Collections.emptyList();
            }
            used[min] = true;
            mst.get(min).add(new IntPair(parent[min], distance[min]));
            mst.get(parent[min]).add(new IntPair(min, distance[min]));
            for (int j = 0; j < n; ++j) {
                if (graph[min][j] == Integer.MAX_VALUE) {
                    continue;
                }
                if (distance[j] > graph[min][j]) {
                    distance[j] = graph[min][j];
                    parent[j] = min;
                }
            }
        }
        return mst;
    }

    /**
     * IntPair.a() - index of adjacent node IntPair.b() - link weight
     * 
     * @param graph
     * @return
     */
    public static List<List<IntPair>> densePrim(List<List<IntPair>> graph) {
        // Пусть A множество помеченных вершин
        // Заведем массив A чтобы помечать вершины

        // Для каждого ребра заведем ссылку на минимальное ребро, которое ведет в A
        // Пусть массив таких ссылок - это D

        // Пусть s изначальная вершина
        // "Релаксируем" D по s, т.е. сетим в вершинки, смежные с s связывающее их ребро

        // цикл на n - 1 итераций
        // за проход по D находим минимальное ребро e (a, b), не из A, которое ведет в A
        // - O(n)
        // добавляем b в A
        // добавляем e в MST
        // "Релаксируем" D по b - O(n)

        // Итого n * 2 * O(N) = O(n^2)

        final int n = graph.size();
        final IntPair inf = new IntPair(-1, Integer.MAX_VALUE);

        List<List<IntPair>> mst = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            mst.add(new ArrayList<>());
        }

        // for i element holds minimal bridge from i to A
        List<IntPair> bridges = new ArrayList<>(Collections.nCopies(n, inf));

        List<Boolean> used = new ArrayList<>(Collections.nCopies(n, false));

        int s = 0;
        used.set(s, true);
        for (IntPair link : graph.get(s)) {
            bridges.set(link.a, new IntPair(s, link.b));
        }

        for (int i = 1; i < n; ++i) {
            IntPair min = inf;
            int minIndex = 0;

            for (int j = 0; j < n; ++j) {
                if (used.get(j)) {
                    continue;
                }
                if (min.b > bridges.get(j).b) {
                    min = bridges.get(j);
                    minIndex = j;
                }
            }
            if (min == inf) {
                return Collections.emptyList();
            }

            mst.get(minIndex).add(new IntPair(min.a, min.b));
            mst.get(min.a).add(new IntPair(minIndex, min.b));

            used.set(minIndex, true);

            for (IntPair link : graph.get(minIndex)) {
                if (bridges.get(link.a).b > link.b) {
                    bridges.set(link.a, new IntPair(minIndex, link.b));
                }
            }
        }

        return mst;
    }

    /**
     * 
     * @param graph
     * @return
     */
    public static List<List<IntPair>> sparsePrim(List<List<IntPair>> graph) {
        // A - множество помеченных вершин

        // Заведем сортирующий сет S для хранения вершин не из A, которые имеют ребро в
        // A
        // Заведем массив P предков для вершин

        // Цикл:
        // Берем вершину v с минимальным ребром из S
        // удаляем минимальное ребро из S
        // Смотрим предка p для вершины v
        // Добавляем в mst ребра <p, v>, <v, p>
        // Релаксируем S по вершине v: обновление дистанций + обновление предков

        final int n = graph.size();
        int[] parent = new int[n]; // index of parent of node with index i
        IntPair[] links = new IntPair[n]; // currently known min link to node with index i
        boolean[] used = new boolean[n];
        final IntPair inf = new IntPair(-1, Integer.MAX_VALUE);
        Arrays.fill(links, inf);
        // set of links from not used nodes to used nodes
        TreeSet<IntPair> set = new TreeSet<IntPair>(
                Comparator.<IntPair>comparingInt(p -> p.b).thenComparingInt(p -> p.a));
        List<List<IntPair>> mst = Stream.generate(() -> new ArrayList<IntPair>()).limit(n)
                .collect(Collectors.toCollection(() -> new ArrayList<List<IntPair>>()));
        int s = 0;
        used[s] = true;
        for (IntPair link : graph.get(s)) {
            parent[link.a] = s;
            links[link.a] = link;
            set.add(link);
        }
        for (int i = 1; i < n; ++i) {
            IntPair nextLink = set.pollFirst();
            if (nextLink == null) {
                return Collections.emptyList();
            }
            int newNode = nextLink.a;
            int dist = nextLink.b;
            used[newNode] = true;
            mst.get(newNode).add(new IntPair(parent[newNode], dist));
            mst.get(parent[newNode]).add(new IntPair(newNode, dist));
            for (IntPair link : graph.get(newNode)) {
                if (used[link.a] || links[link.a].b < link.b) {
                    continue;
                }
                set.remove(links[link.a]);
                parent[link.a] = newNode;
                links[link.a] = link;
                set.add(link);
            }
        }
        return mst;
    }
}
