// Do not forget to change file name
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Sender
{
    public static void main(String args[])
    {
        try
        {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Receiver IP Address : ");
            String ip = sc.nextLine();

            System.out.print("Enter Port Number : ");
            int port = sc.nextInt();
            sc.nextLine();

            DatagramSocket ds = new DatagramSocket();

            while (true)
            {
                System.out.print("Enter Message : ");
                String msg = sc.nextLine();

                byte b[] = msg.getBytes();

                InetAddress ia = InetAddress.getByName(ip);

                DatagramPacket dp = new DatagramPacket(b, b.length, ia, port);

                ds.send(dp);

                if (msg.equalsIgnoreCase("exit"))
                {
                    break;
                }
            }

            ds.close();
        }
        catch (Exception e)
        {
            System.out.println("Error : " + e.getMessage());
        }
    }
}