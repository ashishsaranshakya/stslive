import java.io.*;
import java.net.*;

public class Bob {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            // Receive public key from Eve
            int yd1 = Integer.parseInt(new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine());
            System.out.println("Received public key for Bob: " + yd1);

            int p = 29; // prime number
            int a = 2; // primitive root

            // Generating public key of Bob and sending it to Alice
            System.out.print("Enter private key of Bob: ");
            int xb = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            int yb = (int) Math.pow(a, xb) % p; // Public Key of Bob
            System.out.println("Public key of Bob is: " + yb);
            new PrintStream(socket.getOutputStream()).println(yb);

            // Calculate the secret key
            int k1 = (int) Math.pow(yd1, xb) % p;
            System.out.println("Secret key decrypted by Bob: " + k1);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
