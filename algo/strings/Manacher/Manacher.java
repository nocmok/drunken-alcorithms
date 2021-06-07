public class Manacher {

    public static int[] oddManacher(String str) {
        int[] d = new int[str.length()];
        Arrays.fill(d, 1);
        int l = 0, r = 1; // l - inclusive, r - exclusive
        for (int i = 1; i < str.length(); ++i) {
            if (i < r) {
                d[i] = Integer.min(r - i, d[l + r - 1 - i]);
            }
            while ((i + d[i] < str.length()) && (i - d[i] >= 0) && (str.charAt(i + d[i]) == str.charAt(i - d[i]))) {
                ++d[i];
            }
            if (i + d[i] > r) {
                r = i + d[i];
                l = i - d[i] + 1;
            }
        }
        return d;
    }

    public static int[] evenManacher(String str) {
        int[] d = new int[str.length()];
        // Arrays.fill(d, 0);

        // удобнее принимать за центр правый индекс, так как в таком случае можно
        // утверждать, что d[0] = 0
        int l = 0, r = 1;
        for (int i = 1; i < str.length(); ++i) {
            if (i < r) {
                d[i] = Integer.min(r - i, d[l + r - 1 - i]);
            }
            while ((i + d[i] < str.length()) && (i - d[i] - 1 >= 0)
                    && (str.charAt(i + d[i]) == str.charAt(i - d[i] - 1))) {
                ++d[i];
            }
            if (i + d[i] > r) {
                r = i + d[i];
                l = i - d[i];
            }
        }
        return d;
    }
}
