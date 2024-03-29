/*
Central Queensland University
COIT13229 - Applied Distributed Systems (2024 Term 1)
Campus: External
Assignment 1 - Java Client/Server Application
Student ID: 12184305
Student Name: Daniel Barros
 */
package gym.client.server.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * The UDPClient class is a UDP client application for the gym client/server
 * system.
 *
 * This client application sends a request to the server to retrieve the member
 * list object file. It then receives and displays the response from the server.
 *
 * This class implements the client-side functionality of the gym client/server
 * application using UDP.
 *
 * @author Daniel Barros
 * @version 1.0
 */
public class UDPClient {

    // Declare and initialise server members
    private static String HOST_NAME = "localhost";
    private static int SERVER_PORT = 2205;
    private static DatagramSocket socket;
    private static String OBJECT_FILE_NAME = "memberlistObject";

    /**
     * The main method is the entry point for the UDP client application.
     *
     * It creates a UDP socket, sends a request to the server, receives and
     * displays the response, and closes the socket.
     *
     * @param args command-line arguments (not used)
     */
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
            byte[] buffer = new byte[10_000];

            // Listen for reply
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);

            // Display the reply
            String response = new String(reply.getData(), 0, reply.getLength());
            System.out.println("Server Response: " + response);

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
