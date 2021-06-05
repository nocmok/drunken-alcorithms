public class PrefixFun {

    public static int[] prefixFunction(String str) {
        int[] pi = new int[str.length()];
        for (int i = 1; i < str.length(); ++i) {
            int currPi = pi[i - 1];
            while (currPi > 0 && str.charAt(currPi) != str.charAt(i)) {
                currPi = pi[currPi - 1];
            }
            if (str.charAt(currPi) == str.charAt(i)) {
                pi[i] = currPi + 1;
            }
        }
        return pi;
    }
}
