package A;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Generate {
    static SecureRandom random = new SecureRandom();


    public static BigInteger genRandom(int numberBit) {
        BigInteger value = new BigInteger(numberBit, random);
        value = value.setBit(0);
        value = value.setBit(numberBit - 1);

        return value;
    }


    public static BigInteger genPrime(int numberBit) {

        long time = System.currentTimeMillis();
        int counter = 0;

        BigInteger n;
        if(!Check.isPrepared) {
            Check.preparePrime();
        }

        do {
            counter++;
            n = genRandom(numberBit);
        } while (Check.checkPrime(n));

        System.out.println("Час генерації: " + (System.currentTimeMillis() - time) + " мілісекунд(и)");
        System.out.println("Кількість ітерацій: " + counter);

        return n;
    }
}