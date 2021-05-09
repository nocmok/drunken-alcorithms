import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Solution {

    static class Request {
        String name;
        int k;

        public Request(String name, int k) {
            this.name = name;
            this.k = k;
        }
    }

    public static int solve(Map<String, Integer> storage, List<Request> queue) {
        int minutes = 0;

        while (!queue.isEmpty()) {
            minutes += 1;

            Request req = queue.get(queue.size() - 1);
            int rest = storage.getOrDefault(req.name, 0);

            if (rest == 0) {
                queue.remove(queue.size() - 1);
            } else if (rest >= req.k) {
                storage.put(req.name, rest - req.k);
                queue.remove(queue.size() - 1);
            } else {
                req.k = rest;
                if (queue.size() > 1) {
                    Collections.swap(queue, queue.size() - 1, queue.size() - 2);
                } else {
                    break;
                }
            }
        }

        return minutes;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int m = scanner.nextInt();

            Map<String, Integer> storage = new HashMap<>();

            scanner.nextLine();
            for (int i = 0; i < m; ++i) {
                String[] line = scanner.nextLine().split("\\s+");
                storage.put(line[2], Integer.parseInt(line[0]));
            }

            int n = scanner.nextInt();

            List<Request> queue = new ArrayList<>();

            scanner.nextLine();
            for (int i = 0; i < n; ++i) {
                String[] line = scanner.nextLine().split("\\s+");
                queue.add(new Request(line[2], Integer.parseInt(line[0])));
            }

            Collections.reverse(queue);

            int result = solve(storage, queue);
            System.out.println(result);
        }
    }
}