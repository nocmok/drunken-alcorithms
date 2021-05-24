import java.util.ArrayList;
import java.util.Collections;

public class Solution {

    private static final int UNVISITED = 0;

    private static final int IN_PATH = 1;

    private static final int VISITED = 2;

    /**
     * 
     * @param status 0 - not visited, 1 - in path, 2 - visited
     */
    private boolean hasCycle(List<List<Integer>> graph, int u, int[] status) {
        if (status[u] != UNVISITED) {
            return false;
        }
        status[u] = IN_PATH;
        for (Integer v : graph.get(u)) {
            if (status[v] == IN_PATH) {
                return true;
            }
            if (status[v] == UNVISITED && hasCycle(graph, v, status)) {
                return true;
            }
        }
        status[u] = VISITED;
        return false;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
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
        for (int i = 0; i < numCourses; ++i) {
            if (status[i] == UNVISITED && hasCycle(graph, i, status)) {
                return false;
            }
        }
        return true;
    }
}
