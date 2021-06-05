import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static class Link {
        private int from;
        private int to;
        private int weight;

        public Link(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return List.of(from, to, weight).toString();
        }
    }

    public static List<Link> denseKruskal(List<Link> links, int n) {
        links.sort(Comparator.<Link>comparingInt(l -> l.weight));
        int[] set = new int[n];
        for (int i = 0; i < n; ++i) {
            set[i] = i;
        }
        List<Link> mst = new ArrayList<>();
        for (Link link : links) {
            if (set[link.from] == set[link.to]) {
                continue;
            }
            mst.add(link);
            if (mst.size() >= n - 1) {
                break;
            }
            int aSet = set[link.from];
            for (int i = 0; i < n; ++i) {
                if (set[i] == aSet) {
                    set[i] = set[link.to];
                }
            }
        }
        return mst;
    }

    private static class IntDSU {

        int[] parent;

        int[] rank;

        IntDSU(int n) {
            parent = new int[n];
            rank = new int[n];
        }

        void add(int a) {
            parent[a] = a;
            rank[a] = 1;
        }

        int getSet(int a) {
            if (parent[a] == a) {
                return a;
            }
            return parent[a] = getSet(parent[a]);
        }

        void union(int a, int b) {
            a = getSet(a);
            b = getSet(b);
            if (a == b) {
                return;
            }
            if (rank[a] > rank[b]) {
                parent[b] = a;
                rank[a] += rank[b];
            } else {
                parent[a] = b;
                rank[b] += rank[a];
            }
        }
    }

    public static List<Link> sparseKruskal(List<Link> links, int n) {
        links.sort(Comparator.<Link>comparingInt(l -> l.weight));
        IntDSU dsu = new IntDSU(n);
        for (int i = 0; i < n; ++i) {
            dsu.add(i);
        }
        List<Link> mst = new ArrayList<>();
        for (Link link : links) {
            if (dsu.getSet(link.from) == dsu.getSet(link.to)) {
                continue;
            }
            mst.add(link);
            if (mst.size() >= n - 1) {
                break;
            }
            dsu.union(link.from, link.to);
        }
        return mst;
    }
}
