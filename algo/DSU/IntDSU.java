public class IntDSU {

    private int[] parent;

    private int[] rank;

    public IntDSU(int n) {
        this.parent = new int[n];
        this.rank = new int[n];
    }

    public void union(int a, int b) {
        a = getSet(a);
        b = getSet(b);
        if (a == b) {
            return;
        }
        if (rank[a] > rank[b]) {
            parent[b] = a;
            rank[a] += rank[b];
        } else {
            parent[a] = b;
            rank[b] += a;
        }
    }

    /**
     * 
     * @param a
     * @return set leader for element a
     */
    public int getSet(int a) {
        if (parent[a] == a) {
            return a;
        }
        return parent[a] = getSet(parent[a]);
    }

    public void add(int a) {
        parent[a] = a;
        rank[a] = 1;
    }

    public int rank(int a) {
        return rank[getSet(a)];
    }
}
