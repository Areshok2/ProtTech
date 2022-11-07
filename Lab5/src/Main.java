import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        int KEY = (int) (Math.random() * ((Integer.MAX_VALUE)));

        String str = in.nextLine();

        if (str.length() % 2 != 0)
            str += " ";

        int mid = str.length() / 2;

        for (int i = 0; i < mid; i++)
            left.add((int) str.charAt(i));

        for (int i = mid; i < str.length(); i++)
            right.add((int) str.charAt(i));

        for (int j = 1; j <= 14; j++) {
            for (int i = 0; i < right.size(); i++) {
                right.set(i, right.get(i) ^ (function(left.get(i), j)));
            }
            System.out.println(j + " " + left + " " + right);
            temp = right;
            right = left;
            left = temp;
        }

        for (int j = 14; j >= 1; j--) {
            temp = right;
            right = left;
            left = temp;
            for (int i = 0; i < left.size(); i++) {
                right.set(i, right.get(i) ^ (function(left.get(i), j)));
            }
            System.out.println(j + " " + left + " " + right);
        }

        System.out.print("Output: ");

        for (int i : left)
            System.out.print((char) i);

        for (int i : right)
            System.out.print((char) i);

    }

    private static int function(int a, int b) {
        return a ^ b;
    }
}