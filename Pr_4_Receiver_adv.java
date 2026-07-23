// Do not forget to change file name
import javax.swing.*;
import java.awt.*;
import java.net.*;

class Receiver extends JFrame
{
    JTextArea chatArea;
    JTextField portField;
    JButton startButton;

    DatagramSocket socket;

    Receiver()
    {
        setTitle("UDP Receiver");
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        top.add(new JLabel("Port :"));

        portField = new JTextField("5000", 8);
        top.add(portField);

        startButton = new JButton("Start Receiver");
        top.add(startButton);

        add(top, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        JScrollPane sp = new JScrollPane(chatArea);

        add(sp, BorderLayout.CENTER);

        startButton.addActionListener(e ->
        {
            startReceiver();
            startButton.setEnabled(false);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void startReceiver()
    {
        new Thread(() ->
        {
            try
            {
                int port = Integer.parseInt(portField.getText());

                socket = new DatagramSocket(port);

                chatArea.append("Receiver Started...\n");
                chatArea.append("Waiting for Messages...\n\n");

                while (true)
                {
                    byte b[] = new byte[1024];

                    DatagramPacket dp = new DatagramPacket(b, b.length);

                    socket.receive(dp);

                    String msg = new String(dp.getData(), 0, dp.getLength());

                    chatArea.append("Sender : " + msg + "\n");

                    if (msg.equalsIgnoreCase("exit"))
                    {
                        chatArea.append("\nSender Left.\n");
                        break;
                    }
                }

                socket.close();
            }
            catch (Exception ex)
            {
                chatArea.append("Error : " + ex.getMessage());
            }
        }).start();
    }

    public static void main(String args[])
    {
        new Receiver();
    }
}