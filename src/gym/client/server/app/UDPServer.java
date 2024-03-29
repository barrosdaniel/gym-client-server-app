/*
Central Queensland University
COIT13229 - Applied Distributed Systems (2024 Term 1)
Campus: External
Assignment 1 - Java Client/Server Application
Student ID: 12184305
Student Name: Daniel Barros
 */
package gym.client.server.app;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * The UDPServer class is a UDP server for the gym client/server application.
 * This server listens for client requests, retrieves member data from an object
 * file, and sends the data as a response to the client in a pre-determined
 * format.
 *
 * This class implements the server-side functionality of the gym client/server
 * application using UDP.
 *
 * @author Daniel Barros
 * @version 1.0
 */
public class UDPServer {

    // Declare and initialise constants
    private static int SERVER_PORT = 2205;
    private static String OBJECT_FILE_NAME = "memberlistObject";

    // Declare class members
    private static DatagramSocket socket;
    private static StringBuilder sb;
    private static String messageString;
    private static byte[] messageByte;
    private static ArrayList<Member> allMembersList;

    /**
     * The main method is the entry point for the UDP server application.
     *
     * It listens for client requests, retrieves member data from the object
     * file, prepares and sends the data as a response to the client.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String args[]) {
        allMembersList = new ArrayList();

        try {
            // Create socket
            socket = new DatagramSocket(SERVER_PORT);

            // Prepare buffer to receive client request
            byte[] buffer = new byte[1000];

            while (true) {
                // Listen for client requests until process is stopped
                DatagramPacket request = new DatagramPacket(buffer,
                        buffer.length);
                socket.receive(request);

                // Print client request
                System.out.println("Client Request: " + new String(
                        request.getData(), 0, request.getLength()));

                // Get object file data
                getObjectFileData();

                // Prepare client message
                prepareMessage();

                // Prepare response packet
                DatagramPacket reply = new DatagramPacket(messageByte,
                        messageByte.length, request.getAddress(),
                        request.getPort());

                // Send response to client
                socket.send(reply);

                // Clear members list after sending response. NOTE: This is 
                // needed to avoid duplication in the result set when a new 
                // client sends a request.
                allMembersList.clear();
            }
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

    /**
     * Retrieves member data from the object file and populates the local list
     * of all members.
     */
    private static void getObjectFileData() {
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream(OBJECT_FILE_NAME);
            in = new ObjectInputStream(fis);
            while (true) {
                Member member = (Member) in.readObject();
                if (member instanceof Member) {
                    allMembersList.add((Member) member);
                } else {
                    break;
                }
            }
            in.close();
        } catch (EOFException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepares the message to be sent to the client based on the member data
     * imported from the object data file and contained in the local
     * allMembersList ArrayList.
     */
    private static void prepareMessage() {
        messageByte = new byte[10_000];

        sb = new StringBuilder();
        sb.append("\n");
        sb.append(String.format("|%-20s", "First Name"));
        sb.append(String.format("|%-20s", "Last Name"));
        sb.append(String.format("|%-30s", "Address"));
        sb.append(String.format("|%-20s|", "Phone Number"));
        sb.append("\n========================================"
                + "=======================================================\n");
        for (Member member : allMembersList) {
            sb.append(String.format("|%-20s", member.getFirstName()));
            sb.append(String.format("|%-20s", member.getLastName()));
            sb.append(String.format("|%-30s", member.getAddress()));
            sb.append(String.format("|%-20s|\n", member.getPhoneNumber()));
        }

        messageString = sb.toString();
        messageByte = messageString.getBytes();
    }
}
