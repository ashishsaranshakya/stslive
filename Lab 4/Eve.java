import java.io.*;
import java.net.*;
import java.util.*;

public class Eve {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String keyFromAlice = in.readLine();
            System.out.println("Received key from Alice: " + keyFromAlice);
            String keyFromBob = in.readLine();
            System.out.println("Received key from Bob: " + keyFromBob);

            in.close();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
