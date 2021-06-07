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

    private static long base = 11;

    private static long mod = (long) 1e9 + 7;

    private static long[] pows;

    static void zFunction(long[] str, int[] z) {
        int l = 0;
        int r = 0;
        z[0] = 0;
        for (int i = 1; i < str.length; ++i) {
            z[i] = 0;
            if (i < r) {
                z[i] = Integer.min(r - i, z[i - l]);
            }
            while ((i + z[i] < str.length) && (str[z[i]] == str[i + z[i]])) {
                ++z[i];
            }
            if (i + z[i] > r) {
                r = i + z[i];
                l = i;
            }
        }
    }

    static long pow(long n, long pow) {
        if (pow <= 0) {
            return 1;
        }
        long nsqrt = pow(n, pow / 2);
        long res = (nsqrt * nsqrt) % mod;
        if ((pow & 1l) == 1) {
            res = (res * n) % mod;
        }
        return res;
    }

    static long[][] hashes(int[][] matrix) {
        long[][] hashes = new long[matrix.length][matrix[0].length];
        for (int j = 0; j < matrix[0].length; ++j) {
            hashes[0][j] = matrix[0][j];
        }
        for (int i = 1; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                hashes[i][j] = (hashes[i - 1][j] + (matrix[i][j] * pows[i]) % mod) % mod;
            }
        }
        return hashes;
    }

    /**
     * Get hash of submatrix [iStart:iStart+len)
     */
    static void hash(long[][] hashes, int iStart, int len, long[] hash) {
        for (int j = 0; j < hashes[0].length; ++j) {
            long low = iStart > 0 ? hashes[iStart - 1][j] : 0;
            hash[j] = (hashes[iStart + len - 1][j] + (mod - low)) % mod;
            hash[j] = (hash[j] * pow(pows[iStart], mod - 2)) % mod;
        }
    }

    static long[] hash(int[][] matrix) {
        long[] hash = new long[matrix[0].length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < hash.length; ++j) {
                hash[j] = (hash[j] + (matrix[i][j] * pows[i]) % mod) % mod;
            }
        }
        return hash;
    }

    static boolean checkEquals(int[][] g, int[][] p, int row, int col) {
        int pm = p.length, pn = p[0].length;
        for (int i = 0; i < pm; ++i) {
            if (!Arrays.equals(g[row + i], col, col + pn, p[i], 0, pn)) {
                return false;
            }
        }
        return true;
    }

    private static int[][] asInts(List<String> matrix) {
        int m = matrix.size();
        int n = matrix.get(0).length();
        int[][] ints = new int[m][n];

        int i = 0;
        for (String line : matrix) {
            for (int j = 0; j < n; ++j) {
                ints[i][j] = line.charAt(j) - '0' + 1;
            }
            ++i;
        }
        return ints;
    }

    /*
     * Complete the 'gridSearch' function below.
     *
     * The function is expected to return a STRING. The function accepts following
     * parameters: 1. STRING_ARRAY G 2. STRING_ARRAY P
     */

    public static String gridSearch(List<String> G, List<String> P) {
        int pm = P.size(), pn = P.get(0).length();
        int gm = G.size(), gn = G.get(0).length();

        long pow = 1;
        pows = new long[gm];
        for (int i = 0; i < gm; ++i) {
            pows[i] = pow;
            pow = (pow * base) % mod;
        }

        int[][] p = asInts(P);
        int[][] g = asInts(G);

        long[] pHash = hash(p);
        long[][] gHashes = hashes(g);
        long[] gHash = new long[gn];

        int[] z = new int[pn + gn + 1];
        long[] line = new long[pn + gn + 1];
        System.arraycopy(pHash, 0, line, 0, pn);
        line[pn] = -1;

        for (int i = 0; i + pm <= gm; ++i) {
            hash(gHashes, i, pm, gHash);
            System.arraycopy(gHash, 0, line, pn + 1, gn);
            zFunction(line, z);
            for (int j = 0; j + pn <= gn; ++j) {
                if (z[pn + 1 + j] == pn && checkEquals(g, p, i, j)) {
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
