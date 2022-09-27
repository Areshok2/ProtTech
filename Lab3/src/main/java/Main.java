import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int gcd(int e, int z) {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    public static int[] encrypt(String massage, int P, int Q) {
        String abc = "абвгдеїжзийклмнопрстуфхцчшщєґьіюя_0123456789";
        char[] chABC = abc.toCharArray();
        int e;
        char[] chMassage = massage.toCharArray();
        int[] m = new int[chMassage.length];

        int n = P * Q;
        int eil = (P - 1) * (Q - 1);

        for (e = 2; e < eil; e++) {
            if (gcd(e, eil) == 1)            // e is for public key exponent
            {
                break;
            }
        }
        //System.out.println("Відкритий ключ шифра " + e + " " + n);
        int d = 0;
        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * eil);
            if (x % e == 0)      //d is for private key exponent
            {
                d = x / e;
                break;
            }
        }
        //System.out.println("Закритий ключ шифра " + d + " " + n);

        for (int i = 0; i < chMassage.length; i++) {
            int index = 0;
            while (chMassage[i] != chABC[index]) index++;
            if (index > 43) {
                index -= 43;
            }
            m[i] = index + 1;
        }

        int[] res = new int[chMassage.length];
        for (int i = 0; i < chMassage.length; i++) {
            res[i] = (int) (Math.pow(m[i], e) % n);
        }

        return res;
    }


    public static char[] decrypt(int[] massage, int P, int Q) {
        String abc = "абвгдеїжзийклмнопрстуфхцчшщєґьіюя_0123456789";
        char[] chABC = abc.toCharArray();
        int n = P * Q;
        int eil = (P - 1) * (Q - 1);
        int e;

        for (e = 2; e < eil; e++) {
            if (gcd(e, eil) == 1)            // e is for public key exponent
            {
                break;
            }
        }

        int d = 0;
        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * eil);
            if (x % e == 0)      //d is for private key exponent
            {
                d = x / e;
                break;
            }
        }

        char[] dec = new char[massage.length];

        for (int i = 0; i < massage.length; i++) {
            BigInteger C = BigDecimal.valueOf(massage[i]).toBigInteger();
            BigInteger N = BigInteger.valueOf(n);
            BigInteger msgback = (C.pow(d)).mod(N);

            int[] bitint = new int[massage.length];
            bitint[i] = msgback.intValue();

            //TODO ArrayIndexOutOfBoundsException
            if (bitint[i] > 43) {
                bitint[i] %= 43;
            }


            dec[i] = chABC[bitint[i] - 1];
        }
        return dec;
    }


    public static void main(String[] args) {
        System.out.println("TEST");
        System.out.println("Encrypt - " + Arrays.toString(encrypt("двгупс", 7, 13)));
        System.out.println("Decrypt - " + Arrays.toString(decrypt(encrypt("двгупс", 7, 13), 7, 13)));

        System.out.println("Варіант - 15");
        System.out.println("Encrypt - " + Arrays.toString(encrypt("макс90", 19, 43)));
        System.out.println("Decrypt - " + Arrays.toString(decrypt(new int[]{537, 681, 721, 167, 1, 470, 439, 729}, 19, 43)));

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Введіть повідомлення яке потрібно зашифрувати:");
//        String massage = sc.nextLine();
//        System.out.println("Введіть два простих числа P та Q");
//        System.out.print("P: ");
//        int P = sc.nextInt();
//        System.out.print("Q: ");
//        int Q = sc.nextInt();
//
//        System.out.println("Encrypt - " + Arrays.toString(encrypt(massage, P, Q)));
//        System.out.println(decrypt(new int[] {537, 681, 721, 167, 1, 470, 439, 729},P,Q));


    }
}

