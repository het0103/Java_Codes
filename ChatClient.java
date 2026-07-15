// Practical: Create a Chat application using TCP protocol
// Client Side


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient {

    JFrame frame;
    JTextArea chatArea;
    JTextField messageField;
    JButton sendButton;

    PrintWriter out;
    BufferedReader in;

    ChatClient() {

        try {
            Socket socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            chatArea.append("Connected to server\n");

            Thread readerThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();

        } catch (Exception e) {
            chatArea.append("Connection failed\n");
            e.printStackTrace();
        }

        frame = new JFrame("Chat Client");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.setVisible(true);
    }

    void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && out != null) {
            out.println(message);
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
