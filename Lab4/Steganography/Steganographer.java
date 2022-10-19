import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Steganographer {
   public BufferedImage image; //the image that the Steganographer will encode and decode from
   
   public Steganographer(BufferedImage image) {
      this.image = image;
   }
   
   //encodes a 
   public void encode(File message) throws FileNotFoundException, IOException {
      FileInputStream input = new FileInputStream(message);
      
      //відстежує пікселі
      int x = 0;
      int y = 0;
      
      //endingCounter tracks the EoF sequence "0xff 0xff" that is printed at end of file
      //finished changes when the ending EoF is printed
      //write zeroes tracks if a "0xff" has been seen as file data,
      // and instructs the loop to print "0x00" next to avoid collisions with the EoF

      //endingCounter відстежує послідовність EoF "0xff 0xff", яка друкується в кінці файлу
      //finished змінюється, коли друкується кінцевий EoF
      //write zeroes відстежує, чи «0xff» розглядається як дані файлу,
      // і вказує циклу друкувати "0x00" поруч, щоб уникнути зіткнень з EoF
      int endingCounter = 0;
      boolean finished = false;
      boolean writeZeroesNext = false;
      
      //inBuffer is the next message byte, imageBuffer is the 32-bit RGB value
      //the Rem values are the remaining two bit pairs that need to go from inBuffer to imageBuffer
      //for their respective bytes

      // inBuffer – наступний байт повідомлення, imageBuffer – 32-бітне значення RGB
      //значення Rem - це дві пари бітів, які залишилися, які повинні перейти від inBuffer до imageBuffer
      //для відповідних байтів
      int inBuffer = input.read();
      int imageBuffer = image.getRGB(x, y);
      int inBufferRem = 4;
      int imageBufferRem = 3;
      //the least signifigant 2 bits of the Red, Green, and Blue bytes are changed to be bits of message data
      //молодші 2 біти червоного, зеленого та синього байтів змінюються на біти даних повідомлення
      while (!finished) {
         //shifts bit pairs into last positions
         //зміщує бітові пари на останні позиції
         System.out.println("inBuffer(текст шифрування) - " + inBuffer);
         System.out.println("imageBuffer(32-bit argb в dec) - " + imageBuffer);

         int inBufferShift = (inBufferRem * 2) - 2;     System.out.println("inBufferShift - " + inBufferShift);
         int imageBufferShift = (imageBufferRem * 8) - 8;     System.out.println("imageBufferShift - " + imageBufferShift);
         int bitsIn = (inBuffer >> inBufferShift) & 3;   System.out.println("bitsIn - " + bitsIn);    //pulls out two relavent bits  //витягує два відповідні біти
         int subImage = (imageBuffer >> imageBufferShift) & 0xFC; System.out.println("subImage - " + subImage);//pulls out all bits except last two for relavant byte  //витягує всі біти, крім двох останніх, для відповідного байта
         int combinedByte = bitsIn | subImage;   System.out.println("combinedByte - " + combinedByte);

         //"slots" the new 2 bits in with the original image data
         //"вставляє" нові 2 біти в вихідні дані зображення
         int imageWithSlot = imageBuffer & (~(0xff << imageBufferShift));  System.out.println("imageWithSlot - " + imageWithSlot);
         imageBuffer = imageWithSlot | (combinedByte << imageBufferShift);  System.out.println("imageBuffer - " + imageBuffer);
         System.out.println();

         inBufferRem--;
         imageBufferRem--;
         
         //checks special conditions and refreshes the inBuffer if empty
         //перевіряє спеціальні умови та оновлює inBuffer, якщо він порожній
         if (inBufferRem == 0) {
            if (writeZeroesNext) {
               inBuffer = 0;
               writeZeroesNext = false;
            } else {
               inBuffer = input.read();
            }
            
            if (endingCounter == 2) {
               finished = true;
            } else if (endingCounter == 1) {
               inBuffer = 0xff;
               endingCounter++;
            } else if (inBuffer == -1) {
               endingCounter++;
               inBuffer = 0xff;
            } else if (inBuffer == 0xff) {
               writeZeroesNext = true;
            }           
            
            inBufferRem = 4;
         }
         //refreshes the imageBuffer when empty
         //оновлює imageBuffer, коли він порожній
         if (imageBufferRem == 0) {
            image.setRGB(x, y, imageBuffer);
            x++;
            if (x == image.getWidth()) {
               x = 0;
               y++;
            }
            if (y == image.getHeight()) {
               throw new IOException();
            }
            imageBuffer = image.getRGB(x, y);
            imageBufferRem = 3;
         }
      }
      image.setRGB(x, y, imageBuffer);
      
   }
   
   //decodes the image to an output File file.
   public File decode(File file) throws FileNotFoundException, IOException {
      FileOutputStream output = new FileOutputStream(file);
      
      //tracks the current pixel
      //відстежує пікселі
      int x = 0;
      int y = 0;      
      
      //if previous byte was "0xff", check to see if the next byte is "0xff" or "0x00", to determine EoF
      //якщо попередній байт був "0xff", перевірте, чи є наступний байт "0xff" чи "0x00", щоб визначити EoF
      boolean checkNextByte = false;
      
      //structure mirrors encode
      //структура дзеркального кодування
      int outBuffer = 0;
      int imageBuffer = image.getRGB(x, y);
      int outBufferSize = 0;
      int imageBufferRem = 3;
      
      for (;;) {
         
         //flushes outBuffer and checks special conditions
         //скидає буфер і перевіряє спеціальні умови
         if (outBufferSize == 4) {
            if (checkNextByte) {
               if (outBuffer == 0xff) {
                  break;
               } else {
                  checkNextByte = false;
                  output.write(0xff);
               }
            } else if (outBuffer == 0xff) {
               checkNextByte = true;
            } else {
               output.write(outBuffer);
            }
            outBuffer = 0;
            outBufferSize = 0;
         }
         
         //builds the outBuffer from imageBuffer by grabbing 2 bits from the proper bytes in the 32-bit RGB data
         // будує outBuffer з imageBuffer, захоплюючи 2 біти з відповідних байтів у 32-бітних даних RGB
         int imageBufferShift = (imageBufferRem * 8) - 8;
         int bitsOut = (imageBuffer >> imageBufferShift) & 3;
         
         int outBufferShift = 6 - (outBufferSize * 2);
         outBuffer = (bitsOut << outBufferShift) | outBuffer;
         
         outBufferSize++;
         imageBufferRem--;
         
         //refreshes the imageBuffer
         //оновлює imageBuffer
         if (imageBufferRem == 0) {
            x++;
            if (x == image.getWidth()) {
               x = 0;
               y++;
            }
            if (y == image.getHeight()) {
               throw new IOException();
            }
            imageBuffer = image.getRGB(x, y);
            imageBufferRem = 3;
         }
      }
      return file;
   }
}
/*
* 1101000
* 1
* 1
* 0
* 1
* 0
* 0
* 0
*     10000110 01100110 01000
*     10100100 11111010 1110100
*     10100100 11111010 1110010
*     10010110 11110000 1101110
*
*
*
*
*
*
* */