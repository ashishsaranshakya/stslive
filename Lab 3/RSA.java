import java.math.BigInteger;
import java.util.Scanner;
import java.security.SecureRandom;

public class RSA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a message to encrypt: ");
        String message = scanner.nextLine();

        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(1024, random);
        BigInteger q = BigInteger.probablePrime(1024, random);
        KeyPair keyPair = generateKeyPair(p, q);

        BigInteger[] encryptedMessage = encrypt(keyPair.getPublicKey(), message.getBytes());
        System.out.print("Encrypted message: ");
        for (BigInteger cipher : encryptedMessage) {
            System.out.print(cipher + " ");
        }
        System.out.println();

        String decryptedMessage = decrypt(keyPair.getPrivateKey(), encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    public static KeyPair generateKeyPair(BigInteger p, BigInteger q) {
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        BigInteger d = e.modInverse(phi);
        return new KeyPair(new PublicKey(e, n), new PrivateKey(d, n));
    }

    public static BigInteger[] encrypt(PublicKey publicKey, byte[] message) {
        BigInteger[] cipher = new BigInteger[message.length];
        for (int i = 0; i < message.length; i++) {
            cipher[i] = BigInteger.valueOf(message[i]).modPow(publicKey.getExponent(), publicKey.getModulus());
        }
        return cipher;
    }

    public static String decrypt(PrivateKey privateKey, BigInteger[] encryptedMessage) {
        byte[] decryptedBytes = new byte[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++) {
            decryptedBytes[i] = encryptedMessage[i].modPow(privateKey.getExponent(), privateKey.getModulus()).byteValueExact();
        }
        return new String(decryptedBytes);
    }
}

class PublicKey {
    private BigInteger exponent;
    private BigInteger modulus;

    public PublicKey(BigInteger exponent, BigInteger modulus) {
        this.exponent = exponent;
        this.modulus = modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}

class PrivateKey {
    private BigInteger exponent;
    private BigInteger modulus;

    public PrivateKey(BigInteger exponent, BigInteger modulus) {
        this.exponent = exponent;
        this.modulus = modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}

class KeyPair {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}