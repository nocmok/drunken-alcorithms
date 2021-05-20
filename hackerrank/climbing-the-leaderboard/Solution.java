import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    private static List<Integer> removeDuplicates(List<Integer> arr) {
        ArrayList<Integer> newArr = new ArrayList<>(arr.size());
        Integer prev = null;
        for (Integer num : arr) {
            if (!num.equals(prev)) {
                newArr.add(num);
            }
            prev = num;
        }
        return newArr;
    }

    public static int lowerBound(List<Integer> arr, int val) {
        int a = 0;
        int len = arr.size();
        int res = arr.size();

        while (len > 0) {
            int mid = a + len / 2;
            if (arr.get(mid) > val) {
                len = len - (mid + 1 - a);
                a = mid + 1;
            } else {
                res = mid;
                len = mid - a;
            }
        }

        return res;
    }

    /*
     * Complete the 'climbingLeaderboard' function below.
     *
     * The function is expected to return an INTEGER_ARRAY. The function accepts
     * following parameters: 1. INTEGER_ARRAY ranked 2. INTEGER_ARRAY player
     */

    public static List<Integer> climbingLeaderboard(List<Integer> ranked, List<Integer> player) {
        ranked = removeDuplicates(ranked);
        List<Integer> rank = new ArrayList<>(player.size());
        for (Integer p : player) {
            rank.add(lowerBound(ranked, p) + 1);
        }
        return rank;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int rankedCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> ranked = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt).collect(toList());

        int playerCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> player = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt).collect(toList());

        List<Integer> result = Result.climbingLeaderboard(ranked, player);

        bufferedWriter.write(result.stream().map(Object::toString).collect(joining("\n")) + "\n");

        bufferedReader.close();
        bufferedWriter.close();
    }
}
