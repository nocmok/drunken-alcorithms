import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void sieve(boolean[] prime) {
        long n = prime.length - 1;

        Arrays.fill(prime, true);

        prime[0] = false;
        prime[1] = false;

        for (int i = 2; (long) i * i <= n; ++i) {
            if (!prime[i]) {
                continue;
            }
            for (int j = i * i; j <= n; j += i) {
                prime[j] = false;
            }
        }
    }

    public static int countFactors(int num, List<Integer> primes) {
        int count = 0;
        for (Integer p : primes) {
            if (num <= 1) {
                break;
            }
            while (num % p == 0) {
                ++count;
                num /= p;
            }
        }
        if (num > 1) {
            ++count;
        }
        return count;
    }

    public static int solve(int a, int b, List<Integer> primes) {
        if (a > b || b % a != 0) {
            return 0;
        }
        int divs = countFactors(b / a, primes);
        return divs + 1;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int t = scanner.nextInt();

            // В первой строке дано T — количество тестов, далее идут T строк с числами a и
            // b (0 ≤ T ≤ 20; 1 ≤ a, b ≤ 10^9)

            int n = (int) 1e9;
            int nsqrt = (int) Math.sqrt(n);

            List<Integer> primes = new ArrayList<>();
            boolean[] isprime = new boolean[nsqrt + 1];
            sieve(isprime);
            for(int i = 2; i <= nsqrt; ++i){
                if(isprime[i]){
                    primes.add(i);
                }
            }

            for (int i = 0; i < t; ++i) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int ans = solve(a, b, primes);
                System.out.println(ans);
            }
        }
    }
}