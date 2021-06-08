public class FordBellman {

    public static class Link {
        int a;

        int b;

        int weight;

        public Link(int a, int b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
    }

    /**
     * n - number of nodes Return true if negative cycle detected. Link.a - from,
     * Link.b - to
     */
    public static boolean fordBellman(List<Link> links, int n, int s, int[] d) {
        Arrays.fill(d, Integer.MAX_VALUE);
        d[s] = 0;
        boolean wasChanged = false;
        for (int i = 0; i < n; ++i) {
            wasChanged = false;
            for (Link link : links) {
                if (d[link.a] < Integer.MAX_VALUE && d[link.a] + link.weight < d[link.b]) {
                    d[link.b] = d[link.a] + link.weight;
                    wasChanged = true;
                }
            }
            if (!wasChanged) {
                return false;
            }
        }
        return wasChanged;
    }
}
