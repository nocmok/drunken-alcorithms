public class PrefixFun {

    public static int[] prefixFunction(String str) {
        int[] pi = new int[str.length()];
        prefixFunction(str, pi);
        return pi;
    }

    public static void prefixFunction(String str, int[] pi) {
        for (int i = 1; i < str.length(); ++i) {
            int currPi = pi[i - 1];
            while (currPi > 0 && str.charAt(currPi) != str.charAt(i)) {
                currPi = pi[currPi - 1];
            }
            if (str.charAt(currPi) == str.charAt(i)) {
                pi[i] = currPi + 1;
            }
        }
    }

    public static int countUniqueSubstrings(String str) {
        int unique = 0;
        int[] pi = new int[str.length()];
        for (int i = 1; i <= str.length(); ++i) {
            prefixFunction(str.substring(str.length() - i), pi);
            int max = 0;
            for (int j = 0; j < i; ++j) {
                max = Integer.max(max, pi[j]);
            }
            unique += (i - max);
        }
        return unique;
    }
}
