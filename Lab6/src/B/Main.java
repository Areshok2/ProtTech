package B;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long started = System.nanoTime();
        int i = sc.nextInt();
        int y = sc.nextInt();
        IntStream.rangeClosed(i, y)
                .filter(x -> BigInteger.valueOf(x).isProbablePrime(1))
                .forEach(System.out::println);
        System.out.println("Duration: " + (System.nanoTime() - started) / 1_000_000 + "ms");
    }
}
