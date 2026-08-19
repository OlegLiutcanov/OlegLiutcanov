// PrimeTools.java - solution

public class PrimeTools {

    static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        // Stopping at the square root is safe: if n has a divisor larger than
        // its square root, it must also have the matching smaller one, and
        // that smaller one was already tested.
        for (int divisor = 2; divisor * divisor <= n; divisor++) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    static int countPrimesUpTo(int limit) {
        int count = 0;
        for (int candidate = 2; candidate <= limit; candidate++) {
            if (isPrime(candidate)) {
                count++;
            }
        }
        return count;
    }

    static int nthPrime(int n) {
        if (n < 1) {
            return 0;
        }
        int found = 0;
        int candidate = 1;
        while (found < n) {
            candidate++;
            if (isPrime(candidate)) {
                found++;
            }
        }
        return candidate;
    }

    public static void main(String[] args) {
        int[] samples = { -7, 0, 1, 2, 3, 4, 9, 17, 25, 97 };

        System.out.println("isPrime checks");
        for (int sample : samples) {
            System.out.println("  " + sample + " -> " + isPrime(sample));
        }

        System.out.println();
        System.out.println("How many primes are there up to...");
        int[] limits = { 10, 20, 100, 1000 };
        for (int limit : limits) {
            System.out.println("  " + limit + " -> " + countPrimesUpTo(limit));
        }

        System.out.println();
        System.out.println("Prime by position");
        System.out.println("  the 1st prime is " + nthPrime(1));
        System.out.println("  the 10th prime is " + nthPrime(10));
        System.out.println("  the 100th prime is " + nthPrime(100));
    }
}
