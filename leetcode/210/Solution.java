import java.util.Collections;
import java.util.List;

public class Solution {

    private static final int UNVISITED = 0;

    private static final int IN_PATH = 1;

    private static final int VISITED = 2;

    /**
     * 
     * @return false if graph has cycle
     */
    private static boolean topsort(List<List<Integer>> graph, int root, int[] status, List<Integer> topsort) {
        if (status[root] != UNVISITED) {
            return true;
        }
        status[root] = IN_PATH;
        for (Integer v : graph.get(root)) {
            if (status[v] == IN_PATH) {
                return false;
            }
            if (status[v] == UNVISITED && !topsort(graph, v, status, topsort)) {
                return false;
            }
        }
        status[root] = VISITED;
        topsort.add(root);
        return true;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; ++i) {
            int u = prerequisites[i][1];
            int v = prerequisites[i][0];
            graph.get(u).add(v);
        }
        int[] status = new int[numCourses];
        List<Integer> topsort = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            if (status[i] == UNVISITED && !topsort(graph, i, status, topsort)) {
                return new int[0];
            }
        }
        int[] ans = new int[numCourses];
        for (int i = 0; i < topsort.size(); ++i) {
            ans[i] = topsort.get(topsort.size() - 1 - i);
        }
        return ans;
    }
}