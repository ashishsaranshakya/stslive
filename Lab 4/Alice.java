import java.io.*;
import java.net.*;
import java.util.*;
import java.security.SecureRandom;
import java.math.BigInteger;

public class Alice {
    private static final int PORT = 8080;
    private static final byte[] seed = {(byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x90};
    private static final SecureRandom random = new SecureRandom(seed);
    private static final BigInteger P = BigInteger.probablePrime(1024, random);
    private static final BigInteger G = BigInteger.probablePrime(1024, random);
    private static final BigInteger privateKey = generateRandomBigIntegerInRange(new BigInteger("0"), G);

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BigInteger publicSecret = G.modPow(privateKey, P);
            out.println(publicSecret);
            System.out.println("Alice sent her public secret: " + publicSecret);

            String bobPublicSecret = in.readLine();
            System.out.println("Received Bob's public secret: " + bobPublicSecret);

            BigInteger bobSecret = new BigInteger(bobPublicSecret);
            BigInteger commonSecret = bobSecret.modPow(privateKey, P);
            System.out.println("Common secret: " + commonSecret);

            in.close();
            out.close();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BigInteger generateRandomBigIntegerInRange(BigInteger min, BigInteger max) {
        SecureRandom random = new SecureRandom();
        int numBits = max.subtract(min).bitLength();
        BigInteger result;
        do {
            result = new BigInteger(numBits, random);
        } while (result.compareTo(min) < 0 || result.compareTo(max) >= 0);
        return result;
    }
}
