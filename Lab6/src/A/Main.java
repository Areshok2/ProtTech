package A;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Start!");

        BigInteger n;
        int t = sc.nextInt();
        n = Generate.genPrime(3072);
        System.out.println(n);



        System.out.println("Done!");

    }
}