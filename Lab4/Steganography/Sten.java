import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Sten {

    public void encode(String messageToHide, String imagePath) throws FileNotFoundException, IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        hideMessage(messageToHide, bufferedImage);
        save(bufferedImage);
    }
    public String decode(String imagePath) throws FileNotFoundException, IOException {
        BufferedImage bufImage = ImageIO.read(new File(imagePath));
        char[] message = showMessage(bufImage);
        StringBuilder strBuild = new StringBuilder();
        for (char aMessage : message) {
            strBuild.append(aMessage);
        }
        return strBuild.toString();
    }

    private void save(BufferedImage bufferedImage) throws FileNotFoundException, IOException {
        ImageIO.write(bufferedImage, "bmp", new File("output.bmp"));
    }

    private void hideMessage(String messageStr, BufferedImage bufImage) {
        byte[] message = messageStr.getBytes();

        int width = bufImage.getWidth();
        int height = bufImage.getHeight();
        int rgb = bufImage.getRGB(0, 0);
        rgb = rgb & 0xff00ffff;

        rgb = rgb | message.length << 16;

        bufImage.setRGB(0, 0, rgb);
        int currentChar = 0;
        for (int i = 0; i < width; i++) {
            if (currentChar == message.length) break;
            for (int j = 0; j < height; j++) {
                if (i == 0 && j == 0) continue;
                if (currentChar == message.length) break;
                rgb = bufImage.getRGB(i,j);
                int b = (message[currentChar]);
                rgb = b;
                bufImage.setRGB(i, j, rgb);
                currentChar++;
            }
        }
    }



    private char[] showMessage(BufferedImage bufImage) {
        int width = bufImage.getWidth();
        int height = bufImage.getHeight();
        int rgb = bufImage.getRGB(0, 0);
        int messageSize = (rgb & 0x00ff0000) >>> 16;
        char[] message = new char[messageSize];
        int countChar = 0;
        for (int i = 0; i < width; i++) {
            if (countChar == messageSize) break;
            for (int j = 0; j < height; j++) {
                if (i == 0 && j == 0) continue;
                if (countChar == messageSize) break;
                rgb = bufImage.getRGB(i, j);
                int b = rgb & 0xff;
                int currentChar = b;
                message[countChar] = (char) currentChar;
                countChar++;
            }
        }
        return message;
    }

}





//import javax.imageio.ImageIO;
//        import java.awt.image.BufferedImage;
//        import java.io.File;
//        import java.io.FileNotFoundException;
//        import java.io.IOException;
//        import java.util.Arrays;
//
//
//public class Sten {
//
//
//    public void encode(String messageToHide, String imagePath) throws FileNotFoundException, IOException {
//        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
//        hideMessage(messageToHide, bufferedImage);
//        save(bufferedImage);
//    }
//
//
//    private void save(BufferedImage bufferedImage) throws FileNotFoundException, IOException {
//        ImageIO.write(bufferedImage, "bmp", new File("output.bmp"));
//    }
//
//
//    private void hideMessage(String messageStr, BufferedImage bufImage) {
//        byte[] message = messageStr.getBytes();
//        System.out.println("Message (Hello) - " + Arrays.toString(message));
//        int width = bufImage.getWidth();
//        int height = bufImage.getHeight();
//        int rgb = bufImage.getRGB(0, 0);
//        rgb = rgb & 0xff00ffff;
//        rgb = rgb | message.length << 16;
//        bufImage.setRGB(0, 0, rgb);
//        int currentChar = 0;
//        for (int i = 0; i < width; i++) {
//            if (currentChar == message.length) break;
//            for (int j = 0; j < height; j++) {
//                if (i == 0 && j == 0) continue;
//                if (currentChar == message.length) break;
//                rgb = bufImage.getRGB(i, j);
//                System.out.println("rgb - " + rgb);
//                int a = (rgb >> 24) & 0xff; System.out.println("a - " + a);
//                int r = (rgb >> 16) & 0xff; System.out.println("r - " + r);
//                int g = (rgb >> 8) & 0xff; System.out.println("g - " + g);
//                int b = rgb & 0xff; System.out.println("b - " + b);
//                r = ((r >> 3 & 0xff) << 3 & 0xff) | ((message[currentChar] >> 5) & 0xff); System.out.println("r after - "+r);
//                g = ((g >> 3 & 0xff) << 3 & 0xff) | ((message[currentChar] >> 2) & 0xff); System.out.println("g after - "+g);
//                b = ((b >> 2 & 0xff) << 2 & 0xff) | (message[currentChar]); System.out.println("b after - "+b);
//                System.out.println("r after - "+r);
//                System.out.println("g after - "+g);
//                System.out.println("b after - "+b);
//                rgb = (a << 24) | (r << 16) | (g << 8) | b;
//                System.out.println("rgb after - "+ rgb);
//                System.out.println();
//                bufImage.setRGB(i, j, rgb);
//                currentChar++;
//            }
//        }
//    }
//
//    public String decode(String imagePath) throws FileNotFoundException, IOException {
//        BufferedImage bufImage = ImageIO.read(new File(imagePath));
//        char[] message = showMessage(bufImage);
//        StringBuilder strBuild = new StringBuilder();
//        for (char aMessage : message) {
//            strBuild.append(aMessage);
//        }
//        return strBuild.toString();
//    }
//
//    private char[] showMessage(BufferedImage bufImage) {
//        int width = bufImage.getWidth();
//        int height = bufImage.getHeight();
//        int rgb = bufImage.getRGB(0, 0);
//        int messageSize = (rgb & 0x00ff0000) >>> 16;
//        char[] message = new char[messageSize];
//        int countChar = 0;
//        for (int i = 0; i < width; i++) {
//            if (countChar == messageSize) break;
//            for (int j = 0; j < height; j++) {
//                if (i == 0 && j == 0) continue;
//                if (countChar == messageSize) break;
//                rgb = bufImage.getRGB(i, j);
//                int r = (rgb >> 16) & 0xff;
//                int g = (rgb >> 8) & 0xff;
//                int b = rgb & 0xff;
//                r = (r << 5) & 0xE0;
//                g = (g << 2) & 0x1C;
//                b &= 3;
//                int currentChar = r | g | b;
//                message[countChar] = (char) currentChar;
//                countChar++;
//            }
//        }
//        return message;
//    }
//
//}