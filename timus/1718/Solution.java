import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Scanner;

public class Solution {

    static class MinMax {
        int min;
        int max;

        public MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            Map<String, MinMax> map = new HashMap<>();

            Set<String> failure = new HashSet<>(Arrays.asList("ML", "TL", "WA"));
            String CE = "CE";
            String AC = "AC";

            scanner.nextLine();
            for (int i = 0; i < n; ++i) {
                String[] line = scanner.nextLine().split("\\s+");
                String name = line[0];
                String status = line[1];

                MinMax mm = map.get(name);
                if (mm == null) {
                    mm = new MinMax(0, 0);
                }
                if (failure.contains(status) && (Integer.parseInt(line[2]) == 7)) {
                    mm.min += 1;
                }
                if (!(status.equals(CE) || (!status.equals(AC) && (Integer.parseInt(line[2]) <= 5)))) {
                    mm.max += 1;
                }
                map.put(name, mm);
            }

            int min = 0;
            int max = 0;

            for (MinMax mm : map.values()) {
                min += Integer.min(1, mm.min);
                max += Integer.min(1, mm.max);
            }

            System.out.println(min + " " + max);
        }
    }
}