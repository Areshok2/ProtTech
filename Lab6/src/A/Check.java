package A;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Check {

    static final BigInteger ZERO = BigInteger.ZERO;
    static final BigInteger ONE = BigInteger.ONE;
    static final BigInteger TWO = BigInteger.TWO;

    static List<Integer> listPreparePrime;
    static boolean isPrepared = false;
    static SecureRandom random = new SecureRandom();


    public static void preparePrime() {
        int count = 400;
        int N = 3000;
        boolean[] check = new boolean[N];
        check[0] = false;
        check[1] = false;

        for (int i = 2; i < N; ++i) {
            check[i] = true;
        }

        for (int i = 2; i < N; ++i) {
            if (check[i]) {
                for (int j = 2 * i; j < N; j += i) {
                    check[j] = false;
                }
            }
        }

        listPreparePrime = new ArrayList<>();
        for (int i = 2; i < N; i++) {
            if (check[i]) {
                listPreparePrime.add(i);
                count--;
            }
            if (count == 0) {
                break;
            }
        }

        isPrepared = true;
    }


    public static boolean nativeCheck(BigInteger n) {
        for (int i : listPreparePrime) {
            if (n.mod(BigInteger.valueOf(i)).equals(ZERO)) {
                return true;
            }
        }

        return false;
    }


    public static boolean checkFermat(BigInteger n) {

        return !TWO.modPow(n.subtract(ONE), n).equals(ONE);
    }


    public static boolean Witness(BigInteger a, BigInteger n) {

        BigInteger u = TWO;
        int t = 0;
        long p = 1;
        boolean condition1, condition2;
        do {
            t++;
            p *= 2;
            BigInteger I = BigInteger.valueOf(p);

            condition1 = n.subtract(ONE).mod(I).equals(ZERO);
            if (condition1) {
                u = n.subtract(ONE).divide(I);
            }

            condition2 = u.mod(TWO).equals(ONE);

        } while (!condition2);


        BigInteger[] x = new BigInteger[t + 1];
        x[0] = a.modPow(u, n);
        for (int i = 1; i <= t; ++i) {
            x[i] = x[i - 1].modPow(TWO, n);
            if (x[i].equals(ONE) && !x[i - 1].equals(ONE) && !x[i - 1].equals(n.subtract(ONE))) {
                return true;
            }
        }

        return !x[t].equals(ONE);
    }


    public static boolean RabinMiller(BigInteger n, int s) {
        for (int i = 1; i <= s; ++i) {
            BigInteger a = new BigInteger(n.bitLength(), random);
            if (Witness(a, n)) {
                return true;
            }
        }

        return false;
    }


    public static boolean checkPrime(BigInteger n) {
        if (nativeCheck(n)) {
            return true;
        }

        if (checkFermat(n)) {
            return true;
        }

        return RabinMiller(n, 64);
    }
}
