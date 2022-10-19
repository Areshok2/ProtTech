import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Sten steganograph = new Sten();
        String message = "Hello";
        String path = "sample.bmp";
        steganograph.encode(message, path);
        String decodeMessage = steganograph.decode("output.bmp");
        System.out.println(decodeMessage);
    }
}