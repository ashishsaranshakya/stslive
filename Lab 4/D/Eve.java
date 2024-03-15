import java.io.*;
import java.net.*;

public class Eve {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is listening, waiting for Alice and Bob");

            Socket aliceSocket = serverSocket.accept(); // Alice
            System.out.println("Alice is connected");
            Socket bobSocket = serverSocket.accept(); // Bob
            System.out.println("Bob is connected");

            int p = 29; // prime number
            int a = 2; // primitive root

            // Receiving public key from Alice
            int ya = Integer.parseInt(new BufferedReader(new InputStreamReader(aliceSocket.getInputStream())).readLine());

            // Selecting false key for Bob and sending
            System.out.print("Enter false key for Bob: ");
            int xd1 = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            int yd1 = (int) Math.pow(a, xd1) % p; // public key to be sent to Bob
            System.out.println("Public key for Bob: " + yd1);
            new PrintStream(bobSocket.getOutputStream()).println(yd1);
            System.out.println("Sent public key to Bob");

            // Receive public key from Bob
            int yb = Integer.parseInt(new BufferedReader(new InputStreamReader(bobSocket.getInputStream())).readLine());

            // Selecting false key for Alice
            System.out.print("Enter false key for Alice: ");
            int xd2 = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            int yd2 = (int) Math.pow(a, xd2) % p; // public key to be sent to Alice
            System.out.println("Public key for Alice: " + yd2);
            new PrintStream(aliceSocket.getOutputStream()).println(yd2);
            System.out.println("Sent public key for Alice to Bob");

            // Calculate the secret key of Bob and Alice
            int kBob = (int) Math.pow(yb, xd1) % p;
            int kAlice = (int) Math.pow(ya, xd2) % p;

            System.out.println("Key of Bob calculated by Hacker(Eve): " + kBob);
            System.out.println("Key of Alice calculated by Hacker(Eve): " + kAlice);

            aliceSocket.close();
            bobSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
