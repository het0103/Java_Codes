// Do not forget to change file name
import javax.swing.*;
import java.awt.*;
import java.net.*;

class Sender extends JFrame
{
    JTextField ipField;
    JTextField portField;
    JTextField messageField;

    JTextArea chatArea;

    JButton sendButton;

    DatagramSocket socket;

    Sender()
    {
        setTitle("UDP Sender");
        setSize(550, 500);

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2, 2));

        top.add(new JLabel("Receiver IP"));

        ipField = new JTextField("127.0.0.1");
        top.add(ipField);

        top.add(new JLabel("Port"));

        portField = new JTextField("5000");
        top.add(portField);

        add(top, BorderLayout.NORTH);

        chatArea = new JTextArea();

        chatArea.setEditable(false);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());

        messageField = new JTextField();

        sendButton = new JButton("Send");

        bottom.add(messageField, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);

        try
        {
            socket = new DatagramSocket();
        }
        catch (Exception e)
        {
        }

        sendButton.addActionListener(e -> sendMessage());

        messageField.addActionListener(e -> sendMessage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    void sendMessage()
    {
        try
        {
            String msg = messageField.getText();

            if (msg.length() == 0)
                return;

            byte b[] = msg.getBytes();

            InetAddress ip = InetAddress.getByName(ipField.getText());

            int port = Integer.parseInt(portField.getText());

            DatagramPacket dp = new DatagramPacket(b, b.length, ip, port);

            socket.send(dp);

            chatArea.append("Me : " + msg + "\n");

            messageField.setText("");

            if (msg.equalsIgnoreCase("exit"))
            {
                socket.close();
            }
        }
        catch (Exception ex)
        {
            chatArea.append("Error : " + ex.getMessage() + "\n");
        }
    }

    public static void main(String args[])
    {
        new Sender();
    }
}