import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Given a string expression of numbers and operators, 
// return all possible results from computing all the different possible ways to group numbers and operators. 
// You may return the answer in any order.

// Constraints:
// * 1 <= expression.length <= 20
// * expression consists of digits and the operator '+', '-', and '*'.

class Solution {

    private static Set<String> ops = Set.of("*", "+", "-");

    public List<Integer> diffWaysToCompute(String expression) {

        List<String> tokens = tokenize(expression);

        List<Integer> map = new ArrayList<>();

        for (int i = 0; i < tokens.size(); ++i) {
            if (ops.contains(tokens.get(i))) {
                map.add(i);
            }
        }

        List<Integer> result = evaluate(tokens, 0, tokens.size());

        return result;
    }

    public List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < expression.length(); ++i) {
            if (ops.contains(Character.toString(expression.charAt(i)))) {
                tokens.add(expression.substring(start, i));
                tokens.add(Character.toString(expression.charAt(i)));
                start = i + 1;
            }
        }
        tokens.add(expression.substring(start));
        return tokens;
    }

    public int apply(int a, int b, String op) {
        switch (op) {
        case "+":
            return a + b;
        case "-":
            return a - b;
        case "*":
            return a * b;
        default:
            throw new UnsupportedOperationException("unknown operation " + op);
        }
    }

    public List<Integer> evaluate(List<String> tokens, int start, int len) {
        if (len <= 1) {
            return List.of(Integer.parseInt(tokens.get(start)));
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = start; i < start + len; ++i) {
            if (ops.contains(tokens.get(i))) {
                List<Integer> left = evaluate(tokens, start, i - start);
                List<Integer> right = evaluate(tokens, i + 1, start + len - i - 1);

                for (Integer a : left) {
                    for (Integer b : right) {
                        ans.add(apply(a, b, tokens.get(i)));
                    }
                }
            }
        }

        return ans;
    }

}