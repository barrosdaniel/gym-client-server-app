package gym.client.server.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPClient {

    private static String Username;
    private static String hostName;

    public static void main(String args[]) {

        //The first argument is the message to send to the server. 
        //The second argument is the name of the server.
        DatagramSocket aSocket = null;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name:");
        Username = input.nextLine();
        //you can uncomment and read the input to set hostname
        // System.out.print("Enter the Hostname, the copmuter IPaddress:");
        // hostName = input.nextLine();
        hostName = "localhost";  //localhost
        try {

            //Create a UDP socket
            aSocket = new DatagramSocket();

            //Prepare the message to send to the server
            byte[] m = Username.getBytes();
            InetAddress aHost = InetAddress.getByName(hostName);

            //Agreed port
            int serverPort = 6789;

            //Create a UDP datagram
            DatagramPacket request
                    = new DatagramPacket(m, Username.length(), aHost, serverPort);

            //Send the request
            aSocket.send(request);

            //Prepare a buffer to receive the reply from the server
            byte[] buffer = new byte[1000];

            //Waiting for reply
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);

            //Display the reply
            String response = new String(reply.getData(), 0, reply.getLength());
            System.out.print("Server Response: " + response);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
    }

}
