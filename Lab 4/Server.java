import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 8080;
    private static Map<PrintWriter, String> map = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Echo Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                map.put(writer, null);

                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket, writer));
                clientHandlerThread.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendHistory(PrintWriter writer) {
        for (Map.Entry<PrintWriter, String> e: map.entrySet()) {
            if(!writer.equals(e.getKey())){
                if(e.getValue() != null){
                    writer.println(e.getValue());
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket clientSocket, PrintWriter writer) {
            this.clientSocket = clientSocket;
            this.writer = writer;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                sendHistory(writer);
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received message: " + message);
                    map.put(writer, message);
                    broadcast(message);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String message) {
            for (Map.Entry<PrintWriter, String> e: map.entrySet()) {
                if(!writer.equals(e.getKey())){
                    e.getKey().println(message);
                }
            }
        }
    }
}
