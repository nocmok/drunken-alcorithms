import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Solution {

    // -1 - not visited
    // 0 - visited
    // >0 - depth
    public static boolean hasOddCycles(List<List<Integer>> graph, int v, int d, int[] depths) {
        if (depths[v] != -1) {
            return false;
        }
        depths[v] = d;
        for (Integer n : graph.get(v)) {
            if (depths[n] == 0) {
                continue;
            }
            if (depths[n] > 0) {
                if (((d - depths[n] + 1) & 1) == 1) {
                    return true;
                } else {
                    continue;
                }
            }
            if (hasOddCycles(graph, n, d + 1, depths)) {
                return true;
            }
        }
        depths[v] = 0;
        return false;
    }

    // 0 - red
    // 1 - blue
    // -1 not visited
    public static void paint(List<List<Integer>> graph, int v, int color, int[] colors) {
        if (colors[v] != -1) {
            return;
        }
        colors[v] = color;
        for (Integer n : graph.get(v)) {
            if (colors[n] != -1) {
                continue;
            }
            paint(graph, n, color ^ 1, colors);
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                graph.add(new ArrayList<>());
            }
            for (int i = 0; i < n; ++i) {
                int v;
                while ((v = scanner.nextInt()) != 0) {
                    graph.get(i).add(v - 1);
                    graph.get(v - 1).add(i);
                }
            }
            int[] buf = new int[n];
            Arrays.fill(buf, -1);
            for (int i = 0; i < n; ++i) {
                if (hasOddCycles(graph, i, 1, buf)) {
                    System.out.println(-1);
                    return;
                }
            }
            Arrays.fill(buf, -1);
            for (int i = 0; i < n; ++i) {
                paint(graph, i, 0, buf);
            }
            for (int i = 0; i < n; ++i) {
                System.out.print(buf[i]);
            }
            System.out.println();
        }
    }
}