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
        int linksInMST = 0;
        for (Link link : links) {
            if (set[link.from] == set[link.to]) {
                continue;
            }
            mst.add(link);
            ++linksInMST;
            if (linksInMST >= n - 1) {
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
}
