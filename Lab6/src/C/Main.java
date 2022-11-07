package C;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number: ");
        double x = sc.nextDouble();
        findSqrt(x);
    }

    static int count = 0;
    public static void findSqrt(double x) {
        double t;
        if (count >= 100)
            return;
        t = Math.sqrt(x);
        count++;
        System.out.println("Number " + count + " - " + t);
        findSqrt(t);
    }
}
