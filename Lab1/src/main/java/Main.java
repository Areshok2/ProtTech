import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(encryption(s, 5));
        System.out.println(Decrypt(encryption(s, 5), 5));
        System.out.println(textToHex(encryption(s, 5)));
    }

    public static String encryption(String text, int step) {
        String res = "";

        char[] textChar = text.toCharArray();
        char[] chars = {'а', 'б', 'в', 'г', 'д', 'е', 'є', 'ж',
                'з', 'и', 'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
                'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я'};

        for (int i = 0; i < textChar.length; i++) {
            int index = 0;
            if (Character.isLowerCase(textChar[i])) {
                while (textChar[i] != chars[index]) index++;
                index += step;
                if (index > 32) {
                    index -= 32;
                }
                textChar[i] = chars[index];
            }
        }
        for (char c : textChar) {
            res += c;
        }
        return res;
    }

    public static String Decrypt(String text, int step) {
        String res = "";

        char[] textChar = text.toCharArray();
        char[] chars = {'а', 'б', 'в', 'г', 'д', 'е', 'є', 'ж',
                'з', 'и', 'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
                'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я'};

        for (int i = 0; i < textChar.length; i++) {
            int index = 0;
            if (Character.isLowerCase(textChar[i])) {
                while (textChar[i] != chars[index]) index++;
                index -= step;
                if (index < 0) {
                    index += 32;
                }
                textChar[i] = chars[index];
            }
        }
        for (char c : textChar) {
            res += c;
        }
        return res;
    }

    public static String textToHex(String text) {
        Charset encodingType = StandardCharsets.UTF_8;
        byte[] buf;
        buf = text.getBytes(encodingType);
        char[] HEX_CHARS = "0123456789abcdef".toCharArray();
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }


}

