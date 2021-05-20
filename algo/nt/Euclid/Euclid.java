public class Euclid {

    /**
     * Euclidian algorithm:
     * 
     * a > b
     * gcd(a, b) = gcd(b, a % b)
     */
    public static int gcd(int a, int b) {
        if (a == 0 && b == 0) {
            return 0;
        }
        while (Integer.min(a, b) > 0) {
            if (a > b) {
                a %= b;
            } else {
                b %= a;
            }
        }
        return Integer.max(a, b);
    }

    /**
     * Expanded Euclidian algorithm:
     * 
     * a > b
     * a * x + b * y = gcd(a, b)
     * b * x1 + (a % b) * y1 = gcd(a, b) = b * x1 + (a - b * [a/b]) * y1
     * b (x1 - y1 * [a/b]) + a * y1 = gcd(a, b)
     * x = y1, y = x1 - y1 * [a/b] 
     * 
     * 
     * @return [0] - gcd, [1] - x (factor for max), [2] - y (factor for min)
     */
    public static int[] egcd(int a, int b) {
        int min = Integer.min(a, b);
        int max = Integer.max(a, b);
        if (min == 0) {
            return new int[] { max, 1, 0 };
        }
        int[] prev = egcd(min, max % min);
        int x = prev[2];
        int y = prev[1] - prev[2] * (max / min);
        return new int[] { prev[0], x, y };
    }

    public static int inverse(int n, int mod) {
        n %= mod;
        return egcd(mod, n)[2];
    }
}