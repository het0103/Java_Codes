// Practical: Create a Chat application using TCP protocol
// Server Side


import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            ClientHandler handler = new ClientHandler(clientSocket);
            handler.start();
        }
    }

    static class ClientHandler extends Thread {

        Socket socket;
        PrintWriter out;
        BufferedReader in;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Welcome back");

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(message);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }
}
