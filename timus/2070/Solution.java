import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static int log2(long n) {
        int pow = 0;
        long num = 1;
        while ((num < n) && (num < (1L << 31))) {
            num *= 2;
            pow += 1;
        }
        if (num > n) {
            pow -= 1;
        }
        return pow;
    }

    public static int sqrt(long n) {
        long sqrt = 0;
        for (; sqrt * sqrt < n; ++sqrt)
            ;
        if (sqrt * sqrt > n) {
            sqrt -= 1;
        }
        return (int) sqrt;
    }

    public static void sieve(boolean[] primes) {
        Arrays.fill(primes, true);
        Arrays.fill(primes, 0, 2, false);

        int n = primes.length - 1;

        for (int i = 2; i * i <= n; ++i) {
            if (!primes[i]) {
                continue;
            }
            for (int j = i * i; j <= n; j += i) {
                primes[j] = false;
            }
        }
    }

    public static long solve(long a, long b) {
        int bSqrt = (int) sqrt(b);
        int bLog = log2(b);
        int nPrimes = Integer.max(bSqrt, bLog + 1);
        boolean[] primes = new boolean[nPrimes + 1];

        sieve(primes);

        long counter = 0;

        for (int i = 2; i <= bSqrt; ++i) {
            if (!primes[i]) {
                continue;
            }

            int pow = 2;
            BigInteger i2 = BigInteger.valueOf(((long) i) * i);
            BigInteger num = i2;

            for (; num.compareTo(BigInteger.valueOf(b)) <= 0; num = num.multiply(i2), pow += 2) {
                if (num.compareTo(BigInteger.valueOf(a)) < 0) {
                    continue;
                }
                if (primes[pow + 1]) {
                    ++counter;
                }
            }
        }

        return b - a + 1 - counter;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            long a = scanner.nextLong();
            long b = scanner.nextLong();

            /**
             * Let f(x) - number of positive dividers of x Let p1^a1 * p2^a2 * ... pk^ak -
             * factorization of x f(x) = (a1 + 1) * (a2 + 1) * ... * (ak + 1)
             *
             * f(x) is prime only if x is power of prime.
             */

            long ans = solve(a, b);
            System.out.println(ans);
        }
    }
}