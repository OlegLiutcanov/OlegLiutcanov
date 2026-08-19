// PrimeTools.java
//
// GOAL: write three methods that build on each other. Each one calls the one
// before it, which is exactly how real programs are put together.
//
// A prime number is a whole number greater than 1 whose only divisors are 1
// and itself. So 2, 3, 5, 7, 11 are prime; 1, 4, 9 and every negative number
// are not.
//
// Run it with:  java PrimeTools.java
//
// EXPECTED OUTPUT when you are finished:
//
// isPrime checks
//   -7 -> false
//   0 -> false
//   1 -> false
//   2 -> true
//   3 -> true
//   4 -> false
//   9 -> false
//   17 -> true
//   25 -> false
//   97 -> true
//
// How many primes are there up to...
//   10 -> 4
//   20 -> 8
//   100 -> 25
//   1000 -> 168
//
// Prime by position
//   the 1st prime is 2
//   the 10th prime is 29
//   the 100th prime is 541

public class PrimeTools {

    // TODO 1: return true when n is prime, false otherwise.
    //
    // Start with a guard clause for the numbers that are never prime
    // (anything below 2), then try dividing n by every candidate from 2
    // upwards. If any division comes out even - that is, n % d == 0 - you
    // already have your answer, so return false immediately.
    //
    // A divisor never needs to be checked past the square root of n, so
    // "d * d <= n" is a good loop condition. Plain "d < n" also works and is
    // easier to see at first; make it correct before you make it fast.
    static boolean isPrime(int n) {
        return false;
    }

    // TODO 2: return how many primes there are from 2 up to and including
    // limit. Loop over the numbers and let isPrime do the deciding - you
    // should not need the % operator anywhere in this method.
    static int countPrimesUpTo(int limit) {
        return 0;
    }

    // TODO 3: return the nth prime, counting from 1. nthPrime(1) is 2,
    // nthPrime(2) is 3, nthPrime(3) is 5.
    //
    // You do not know in advance how far you have to search, so a while loop
    // fits better than a for loop here: keep testing numbers, keep count of
    // the primes you find, and stop when the count reaches n.
    static int nthPrime(int n) {
        return 0;
    }

    // main is already written for you. Do not change it.
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
