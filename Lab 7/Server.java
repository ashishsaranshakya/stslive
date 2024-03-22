import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import java.util.stream.Collectors;


public class Server {
    public static void main(String[] args) throws Exception {
        // Generate key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        // Send data and signature
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started. Listening on port 8000...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected: " + socket.getInetAddress().getHostName());

        // Sign data
        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); 
        dsa.initSign(priv);
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter message to send: ");
        String str = sc.nextLine();
        byte[] strByte = str.getBytes("UTF8");
        dsa.update(strByte);
        byte[] realSig = dsa.sign();

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(str);
        out.writeObject(realSig);
        System.out.println("Signature: " + new String(realSig).chars()
                .mapToObj(c -> String.format("%02X", c))
                .collect(Collectors.joining()));
        out.writeObject(pub);

        out.close();
        socket.close();
        serverSocket.close();
    }
}
