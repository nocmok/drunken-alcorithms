import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    static final int mod = (int) 1e9;

    static class SegmentTree {

        int[] tree;

        int k;

        int n;

        public SegmentTree(int k) {
            this.k = k;
            int pow2 = 1;
            while (pow2 < k) {
                pow2 <<= 1;
            }
            this.n = pow2;
            this.tree = new int[2 * pow2];
        }

        int parent(int i) {
            return i >>> 1;
        }

        int left(int i) {
            return (i << 1);
        }

        int right(int i) {
            return (i << 1) + 1;
        }

        public void set(int i, int sum) {
            i += n;
            tree[i] = sum;
            while ((i = parent(i)) > 0) {
                tree[i] = (int) (((long) tree[left(i)] + tree[right(i)]) % mod);
            }
        }

        // sum on segment [0, a]
        public int sum(int b) {
            int a = n;
            b = n + b;

            int sum = 0;
            while (a <= b) {
                if ((a & 1) == 1) {
                    sum = (int) (((long) sum + tree[a]) % mod);
                    a += 1;
                }
                if ((b & 1) == 0) {
                    sum = (int) (((long) sum + tree[b]) % mod);
                    b -= 1;
                }
                a = parent(a);
                b = parent(b);
            }

            return sum;
        }
    }

    public static long solve(int[] t, int K) {
        SegmentTree[] sts = new SegmentTree[K + 1];
        int n = t.length;
        for (int k = 1; k <= K; ++k) {
            sts[k] = new SegmentTree(n + 1);
        }
        for (int i = n - 1; i >= 0; --i) {
            sts[1].set(t[i], 1);
            for (int k = 2; k <= K; ++k) {
                sts[k].set(t[i], sts[k - 1].sum(t[i] - 1));
            }
        }
        int result = sts[K].sum(n);
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();

            int[] t = new int[n];
            for (int i = 0; i < n; ++i) {
                t[i] = scanner.nextInt();
            }

            long result = solve(t, k);
            System.out.println(result);
        }
    }
}