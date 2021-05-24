import java.util.List;

public class TopSort {

    public static void topSort(List<List<Integer>> graph, List<Integer> topsort) {
        int n = graph.size();

        int[] in = new int[n]; // number of input links
        int[] out = new int[n]; // number of output links

        for (int i = 0; i < n; ++i) {
            out[i] = graph.get(i).size();
            for (int j = 0; j < graph.get(i).size(); ++j) {
                in[graph.get(i).get(j)] += 1;
            }
        }

        List<Integer> roots = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (in[i] == 0) {
                roots.add(i);
            }
        }

        while (!roots.isEmpty()) {
            int root = roots.get(roots.size() - 1);
            roots.remove(roots.size() - 1);
            topsort.add(root);
            for (int i = 0; i < graph.get(root).size(); ++i) {
                in[graph.get(root).get(i)] -= 1;
                if (in[graph.get(root).get(i)] == 0) {
                    roots.add(graph.get(root).get(i));
                }
            }
        }
    }

    /**
     * 
     * @param topsort - topological sort for specified graph in reverse order
     */
    public static void topSortDfs(List<List<Integer>> graph, int root, boolean[] used, List<Integer> topsort) {
        if (used[root]) {
            return;
        }
        used[root] = true;
        for (Integer v : graph.get(root)) {
            if (!used[v]) {
                topSortDfs(graph, v, used, topsort);
            }
        }
        topsort.add(root);
    }

    public static void topSortDfs(List<List<Integer>> graph, boolean[] used, List<Integer> topsort) {
        for (int i = 0; i < graph.size(); ++i) {
            if (!used[i]) {
                topSortDfs(graph, i, used, topsort);
            }
        }
    }
}