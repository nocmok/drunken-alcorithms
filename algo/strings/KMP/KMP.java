import java.util.List;
import java.util.ArrayList;

/**
 * Knuth-Morris-Pratt algorithm
 */
public class KMP {

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

    /**
     * Empty pattern matches every character in target string, i.e. for empty
     * pattern output is numbers from 0 to str.length() - 1
     */
    public static List<Integer> kmp(String str, String pattern, String delimeter) {
        int[] pi = prefixFunction(pattern + delimeter + str);
        List<Integer> positions = new ArrayList<>();
        for (int i = pattern.length() - 1; i < str.length(); ++i) {
            if (pi[pattern.length() + 1 + i] == pattern.length()) {
                positions.add(i + 1 - pattern.length());
            }
        }
        return positions;
    }
}
