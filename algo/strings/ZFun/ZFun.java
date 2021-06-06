public class ZFun {

    public static int[] zFunction(String str) {
        int[] z = new int[str.length()];
        int l = 0;
        int r = 0; // index of character after last matching character
        for (int i = 1; i < str.length(); ++i) {
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
        return z;
    }
}