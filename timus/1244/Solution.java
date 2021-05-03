import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static List<Integer> solve(int[] weights, int sum) {
        int[][] dp = new int[weights.length + 1][sum + 1];

        Arrays.fill(dp[0], 0);
        for (int i = 0; i < dp.length; ++i) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < dp.length; ++i) {
            for (int j = 1; j < dp[i].length; ++j) {
                // i - first i items
                // j - target weight
                // dp[i][j] =
                dp[i][j] = dp[i - 1][j];
                if (weights[i - 1] <= j) {
                    dp[i][j] += dp[i - 1][j - weights[i - 1]];
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        if (dp[weights.length][sum] == 0) {
            result.add(0);
        } else if (dp[weights.length][sum] > 1) {
            result.add(-1);
        } else {

            List<Integer> cards = new ArrayList<>();

            int k = weights.length;
            int w = sum;

            while (k > 0 && w > 0) {
                if (weights[k - 1] <= w && dp[k - 1][w - weights[k - 1]] == 1) {
                    cards.add(k);
                    w -= weights[k - 1];
                    k -= 1;
                } else {
                    k -= 1;
                }
            }

            for (int i = 0; i < weights.length; ++i)
                result.add(i + 1);
            result.removeAll(cards);
        }

        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int totalWeight = scanner.nextInt();
            int n = scanner.nextInt();
            int[] weights = new int[n];
            for (int i = 0; i < n; ++i) {
                weights[i] = scanner.nextInt();
            }
            List<Integer> result = solve(weights, totalWeight);
            for (Integer i : result) {
                System.out.print(i + " ");
            }
        }
    }
}