import java.util.Arrays;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        System.out.println("TEST");
        System.out.println(encrypt("тигр", "легіон_27"));
        System.out.println(decrypt("тигр", "хндитб4и3ї"));
        System.out.println();
        System.out.println("Варіант 15");
        System.out.println(encrypt("братимир", "тяньаньмень_1989"));
        System.out.println(decrypt("добривод", "гб2фшоцои2бчфхю"));

    }

    public static String encrypt(String gamma, String massage) {
        String res = "";
        String abc = "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя_0123456789";
        char[] chABC = abc.toCharArray();
        char[] chMassage = massage.toCharArray();
        char[] chGamma = gamma.toCharArray();
        int[] m = new int[chMassage.length];
        int[] g = new int[chGamma.length];
        int[] c = new int[chMassage.length];

        for (int i = 0; i < chGamma.length; i++) {
            int index = 0;
            while (chGamma[i] != chABC[index]) index++;
            if (index > 43) {
                index -= 43;
            }
            g[i] = index;
        }
        for (int i = 0; i < chMassage.length; i++) {
            int index = 0;
            while (chMassage[i] != chABC[index]) index++;
            if (index > 43) {
                index -= 43;
            }
            m[i] = index;
        }
        int index = 0;
        for (int i = 0; i < m.length; i++) {
            c[i] = (m[i] + g[index++]) % chABC.length;
            if (index > chGamma.length - 1) index = 0;
        }

        for (int i = 0; i < c.length; i++) {
            System.out.print(chABC[c[i] + 1]);
        }
        return "";
    }


    public static String decrypt(String gamma, String massage) {
        String res = "";
        String abc = "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя_0123456789";
        char[] chABC = abc.toCharArray();
        char[] chMassage = massage.toCharArray();
        char[] chGamma = gamma.toCharArray();
        int[] m = new int[chMassage.length];
        int[] g = new int[chGamma.length];
        int[] c = new int[chMassage.length];

        for (int i = 0; i < chGamma.length; i++) {
            int index = 0;
            while (chGamma[i] != chABC[index]) index++;
            if (index > 43) {
                index -= 43;
            }

            g[i] = index;
        }
        for (int i = 0; i < chMassage.length; i++) {
            int index = 0;
            while (chMassage[i] != chABC[index]) index++;
            if (index > 43) {
                index -= 43;
            }

            m[i] = index;
        }
        int index = 0;
        for (int i = 0; i < m.length; i++) {
            c[i] = ((m[i] - g[index++]) + 43) % chABC.length;
            if (c[i] == 0) c[i] = 44;
            if (index > chGamma.length - 1) index = 0;
        }

        for (int i = 0; i < c.length; i++) {
            System.out.print(chABC[c[i]]);
        }
        return "";
    }

}


//Шифр Вернама
//import java.util.Random;
//
//import java.util.Scanner;
//
//
//
//
//public class Main {
//
//    private static String plainText = "";
//
//    private static String key = "";
//
//    private static String encryptedMessage = "";
//
//    private static int messageSize;
//
//    private static Scanner scanner;
//
//    private static String option;
//
//    public static String encrypt(String plainText, String key) {
//
//        String encryptedMessage = "";
//
//        char letter;
//
//        int index;
//        for (int i = 0; i < plainText.length(); i++) {
//
//            index = plainText.toCharArray()[i] + key.toCharArray()[i];
//            if (index < 26) {
//
//                index = index;
//
//            } else {
//
//                index = index % 26;
//
//            }
//
//            letter = (char) ('A' + index);
//
//            System.out.println(
//
//                    plainText.toCharArray()[i] +
//
//                            " + " + key.toCharArray()[i] +
//
//                            " = " + index + " " + letter);
//
//            encryptedMessage += letter;
//
//        }
//        return encryptedMessage;
//
//    }
//
//    public static String decrypt(String plainText, String key) {
//
//        String decryptedMessage = "";
//
//        char letter;
//
//        int index;
//        for (int i = 0; i < plainText.length(); i++) {
//
//            index = plainText.toCharArray()[i] - key.toCharArray()[i];
//            if (index > 0) {
//
//                index = index;
//
//            } else {
//
//                index += 26;
//
//            }
//
//            if (index == 26) {
//
//                index = 0;
//
//            }
//
//            letter = (char) ('A' + index);
//
//            System.out.println(
//
//                    plainText.toCharArray()[i] +
//
//                            " - " + key.toCharArray()[i] +
//
//                            " = " + index + " " +
//
//                            letter);
//
//            decryptedMessage += letter;
//
//        }
//        return decryptedMessage;
//
//    }
//
//    public static void main(String[] args) {
//
//        scanner = new Scanner(System.in);
//
//        Random random = new Random();
//        System.out.println("Enter your message:");
//        plainText = scanner.nextLine();
//
//        plainText = plainText.replaceAll("\\s", "").trim().toUpperCase();
//
//        plainText = plainText.replace("1", "ONE");
//
//        plainText = plainText.replace("2", "TWO");
//
//        plainText = plainText.replace("3", "THREE");
//
//        plainText = plainText.replace("4", "FOUR");
//
//        plainText = plainText.replace("5", "FIVE");
//
//        plainText = plainText.replace("6", "SIX");
//
//        plainText = plainText.replace("7", "SEVEN");
//
//        plainText = plainText.replace("8", "EIGHT");
//
//        plainText = plainText.replace("9", "NINE");
//
//        plainText = plainText.replace("0", "ZERO");
//        messageSize = plainText.length();
//        System.out.println("The modified message is: " + plainText + "\n");
//
//        for (int i = 0; i < messageSize; i++) {
//
//            key += (char) ('A' + random.nextInt(26));
//
//        }
//        System.out.println("The key is:" + key + "\n");
//        encryptedMessage = encrypt(plainText, key);
//        System.out.println("The encrypted message is: " + encryptedMessage + "\n");
//        encryptedMessage = decrypt(encryptedMessage, key);
//        System.out.println("The source text is: " + encryptedMessage);
//    }
//
//}