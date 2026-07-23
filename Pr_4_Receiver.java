// Do not forget to change file name
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Receiver
{
    public static void main(String args[])
    {
        try
        {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter port number (5000-8000): ");
            int port = sc.nextInt();

            DatagramSocket ds = new DatagramSocket(port);

            System.out.println("Receiver started...");
            System.out.println("Waiting for messages...");

            byte b[] = new byte[1024];

            while (true)
            {
                DatagramPacket dp = new DatagramPacket(b, b.length);

                ds.receive(dp);

                String str = new String(dp.getData(), 0, dp.getLength());

                System.out.println("Sender : " + str);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error : " + e.getMessage());
        }
    }
}