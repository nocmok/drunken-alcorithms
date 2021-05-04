import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {

    /**
     * 2^k - initial number of participants
     */
    public static int solve(Map<String, Integer> map, int k) {
        int max = Collections.max(map.values());
        // i = result
        // 2^k / 2^(i) < max
        // 2^i > 2^k / max
        // i > log(2^k / max)

        int rounds = 0;
        int participants = 1 << k;

        while (participants >= 2 * max) {
            ++rounds;
            participants /= 2;
        }

        return rounds;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int k = scanner.nextInt();
            scanner.nextLine();
            int n = 1 << k;

            Map<String, Integer> cntMap = new HashMap<>();

            for (int i = 0; i < n; ++i) {
                String[] line = scanner.nextLine().split("\\s+");
                cntMap.put(line[1], cntMap.getOrDefault(line[1], 0) + 1);
            }

            System.out.println(solve(cntMap, k));
        }
    }
}