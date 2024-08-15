import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final Set<PrintWriter> clientWriters = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Authentication
                out.println("Enter username:");
                String username = in.readLine();
                out.println("Enter password:");
                String password = in.readLine();
                if (!UserAuthentication.authenticate(username, password)) {
                    out.println("Authentication failed!");
                    System.out.println("Authentication failed for user: " + username);
                    socket.close();
                    return;
                }
                out.println("Authentication successful!");
                System.out.println("Authentication successful for user: " + username);

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    String decryptedMessage = EncryptionUtils.decrypt(message);
                    System.out.println("Received message: " + decryptedMessage);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(EncryptionUtils.encrypt(decryptedMessage));
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }
}
