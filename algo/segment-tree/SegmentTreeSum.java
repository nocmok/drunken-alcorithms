import java.util.Arrays;

public class SegmentTreeSum {

    private int[] tree;

    private int n;

    public SegmentTreeSum(int[] arr) {
        int n = 1;
        while (n < arr.length) {
            n <<= 1;
        }
        this.n = n;
        this.tree = new int[2 * n];
        buildTree(arr);
    }

    private void buildTree(int[] arr) {
        Arrays.fill(tree, 0);
        System.arraycopy(arr, 0, tree, n, arr.length);
        buildTree(1);
    }

    private int parent(int i) {
        return i >>> 1;
    }

    private int left(int i) {
        return i << 1;
    }

    private int right(int i) {
        return (i << 1) + 1;
    }

    private int buildTree(int root) {
        if (root >= n) {
            return tree[root];
        }
        tree[root] = buildTree(left(root)) + buildTree(right(root));
        return tree[root];
    }

    public void set(int i, int value) {
        i = n + i;
        int d = value - tree[i];
        tree[i] = value;
        while ((i = parent(i)) > 0) {
            tree[i] += d;
        }
    }

    public int get(int i) {
        return tree[n + i];
    }

    public int sum(int a, int b) {
        a = a + n;
        b = b + n;
        int s = 0;
        while (a <= b) {
            if ((a & 1) == 1) {
                s += tree[a];
                a += 1;
            }
            if ((b & 1) == 0) {
                s += tree[b];
                b -= 1;
            }
            a = parent(a);
            b = parent(b);
        }
        return s;
    }
}