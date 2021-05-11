import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Comparable;

public class Solution {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int l = scanner.nextInt();
            int t = scanner.nextInt();
            int va = scanner.nextInt();
            int vb = scanner.nextInt();
            int n = scanner.nextInt();

            int ta = t;
            int tb = t;

            for (int i = 0; i < n; ++i) {
                int id = scanner.nextInt();
                scanner.nextInt();
                int duration = scanner.nextInt();
                if (id == 1) {
                    ta -= duration;
                } else {
                    tb -= duration;
                }
            }

            int result = (ta * va + tb * vb) / l;
            System.out.println(result);
        }
    }
}