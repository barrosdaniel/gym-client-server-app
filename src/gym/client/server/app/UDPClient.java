package gym.client.server.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {

    // Declare and initialise server members
    private static String HOST_NAME = "localhost";
    private static int SERVER_PORT = 2205;
    private static DatagramSocket socket;
    private static String OBJECT_FILE_NAME = "memberlistObject";

    public static void main(String args[]) {

        try {
            // Create socket
            socket = new DatagramSocket();

            // Prepare the message to send to the server
            byte[] message = OBJECT_FILE_NAME.getBytes();
            InetAddress host = InetAddress.getByName(HOST_NAME);

            // Create UDP datagram
            DatagramPacket request = new DatagramPacket(message,
                    OBJECT_FILE_NAME.length(), host, SERVER_PORT);

            // Send request to server
            socket.send(request);

            // Prepare buffer to receive server reply
            byte[] buffer = new byte[1000];

            // Listen for reply
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);

            // Display the reply
            String response = new String(reply.getData(), 0, reply.getLength());
            System.out.print("Server Response: " + response);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

}
