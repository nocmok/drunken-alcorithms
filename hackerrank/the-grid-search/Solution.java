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

    private static void zFunction(String str, int[] z) {
        int l = 0;
        int r = 0; // index of character after last matching character
        for (int i = 1; i < str.length(); ++i) {
            z[i] = 0;
            if (i < r) {
                z[i] = Integer.min(r - i, z[i - l]); // lower estimate for z_i
            }
            while ((i + z[i] < str.length()) && str.charAt(z[i]) == str.charAt(i + z[i])) {
                ++z[i];
            }
            if (i + z[i] > r) {
                r = i + z[i];
                l = i;
            }
        }
    }

    /*
     * Complete the 'gridSearch' function below.
     *
     * The function is expected to return a STRING. The function accepts following
     * parameters: 1. STRING_ARRAY G 2. STRING_ARRAY P
     */

    public static String gridSearch(List<String> G, List<String> P) {
        int gm = G.size();
        int gn = G.get(0).length();

        int pm = P.size();
        int pn = P.get(0).length();

        G = new ArrayList<>(G);
        P = new ArrayList<>(P);

        int[][] z = new int[pm][pn + 1 + gn];
        for (int i = 0; i <= gm - pm; ++i) {
            for (int j = 0; j < pm; ++j) {
                zFunction(P.get(j) + ";" + G.get(i + j), z[j]);
            }
            for (int j = 0; j <= gn - pn; ++j) {
                boolean matched = true;
                for (int r = 0; r < pm; ++r) {
                    if (z[r][pn + 1 + j] != pn) {
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    return "YES";
                }
            }
        }

        return "NO";
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int R = Integer.parseInt(firstMultipleInput[0]);

                int C = Integer.parseInt(firstMultipleInput[1]);

                List<String> G = IntStream.range(0, R).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(toList());

                String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int r = Integer.parseInt(secondMultipleInput[0]);

                int c = Integer.parseInt(secondMultipleInput[1]);

                List<String> P = IntStream.range(0, r).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(toList());

                String result = Result.gridSearch(G, P);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
