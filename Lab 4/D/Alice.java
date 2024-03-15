import java.io.*;
import java.net.*;

public class Alice {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            int p = 29; // prime number
            int a = 2; // primitive root

            // Calculating the public key of Alice and sending
            System.out.print("Enter the private key for Alice: ");
            int xa = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            int ya = (int) Math.pow(a, xa) % p; // Public key of Alice
            new PrintStream(socket.getOutputStream()).println(ya);

            // Receive public key from Eve
            int yd2 = Integer.parseInt(new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine());
            System.out.println("Received public key for Alice: " + yd2);

            // Calculate the secret key
            int k2 = (int) Math.pow(yd2, xa) % p;
            System.out.println("Secret key decrypted by Alice: " + k2);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
