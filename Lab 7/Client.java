import java.io.*;
import java.net.*;
import java.security.*;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) throws Exception {
        // Receive data and signature
        Socket socket = new Socket("localhost", 8000);
        System.out.println("Connected to server...");

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        String str = (String) in.readObject();
        System.out.println("Received message: " + str);
        byte[] realSig = (byte[]) in.readObject();
        System.out.println("Received signature: " + new String(realSig).chars()
                .mapToObj(c -> String.format("%02X", c))
                .collect(Collectors.joining()));
        PublicKey pub = (PublicKey) in.readObject();

        // Verify signature
        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
        dsa.initVerify(pub);
        dsa.update(str.getBytes("UTF8"));
        boolean verifies = dsa.verify(realSig);
        System.out.println("Signature verifies: " + verifies);

        in.close();
        socket.close();
    }
}
