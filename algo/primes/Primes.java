import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Primes {

    /** Eratosthenes sieve */
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

    public static List<Integer> getPrimes(int n) {
        int sqrtn = (int) Math.sqrt(n);

        boolean[] block = new boolean[sqrtn + 1];
        sieve(block);

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < block.length; ++i) {
            if (block[i]) {
                primes.add(i);
            }
        }

        int blocks = (int) (((long) n + block.length - 1) / block.length);

        for (int blockN = 1; blockN < blocks; ++blockN) {
            Arrays.fill(block, true);

            int start = blockN * block.length;
            int len = Integer.min(block.length, n - blockN * block.length);

            for (Integer p : primes) {
                if (p * p > n) {
                    break;
                }

                int startI = (int) (((long) start + p - 1) / p);

                for (long j = (long) startI * p; j < start + len; j += p) {
                    block[(int) (j - start)] = false;
                }
            }

            for (int i = 0; i < len; ++i) {
                if (block[i]) {
                    primes.add(i + start);
                }
            }
        }

        return primes;
    }

    public static int countPrimes(int n) {
        int sqrtn = (int) Math.sqrt(n);

        boolean[] block = new boolean[sqrtn + 1];
        sieve(block);

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < block.length; ++i) {
            if (block[i]) {
                primes.add(i);
            }
        }

        int count = primes.size();

        int blocks = (int) (((long) n + block.length - 1) / block.length);

        for (int blockN = 1; blockN < blocks; ++blockN) {
            Arrays.fill(block, true);

            int start = blockN * block.length;
            int len = Integer.min(block.length, n - blockN * block.length);

            for (Integer p : primes) {

                int startI = (int) (((long) start + p - 1) / p);

                for (long j = (long) startI * p; j < start + len; j += p) {
                    block[(int) (j - start)] = false;
                }
            }

            for (int i = 0; i < len; ++i) {
                if (block[i]) {
                    ++count;
                }
            }
        }

        return count;
    }

    public static List<Integer> factorize(int num, List<Integer> primes) {
        List<Integer> divs = new ArrayList<Integer>();
        for (Integer p : primes) {
            if (num <= 1) {
                break;
            }
            while (num % p == 0) {
                divs.add(p);
                num /= p;
            }
        }
        if (num > 1) {
            divs.add(num);
        }
        return divs;
    }
}