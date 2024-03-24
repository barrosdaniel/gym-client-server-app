package gym.client.server.app;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {

    private static Scanner input = new Scanner(System.in);

    public static void main(String args[]) {
        // Initialise server members
        int SERVER_PORT = 1105;
        String HOST_NAME = "localhost";
        Socket socket = null;
        int nextMemberNumber = 1;

        // Loop through until client process stopped
        while (true) {
            System.out.println("Enter Detail for Member: " + nextMemberNumber);
            // Get member details
            String firstName = getMemberFirstName();
            String lastName = getMemberLastName();
            String address = getMemberAddress();
            String phoneNumber = getMemberPhoneNumber();

            // Assemble client message
            String message = firstName + ":" + lastName + ":" + address + ":"
                    + phoneNumber;

            // Send message to server
            try {
                // Create comms socket
                socket = new Socket(HOST_NAME, SERVER_PORT);

                // Send message to server
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(message);
                System.out.println("Sending Data to Server...............");
                System.out.println(message);

                // Receive server response
                DataInputStream in = new DataInputStream(socket.getInputStream());
                String data = in.readUTF();
                System.out.println("Server Response: " + data + ": " 
                        + nextMemberNumber);
            } catch (UnknownHostException e) {
                System.out.println("Sock:" + e.getMessage());
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("close:" + e.getMessage());
                    }
                }
            }
            System.out.println("---------------------------------------------");
            nextMemberNumber += 1;
        }
    }

    private static String getMemberFirstName() {
        System.out.print("Enter your First Name: \n");
        return input.nextLine();
    }

    private static String getMemberLastName() {
        System.out.print("Enter your Last Name: \n");
        return input.nextLine();
    }

    private static String getMemberAddress() {
        System.out.print("Enter your Address: \n");
        return input.nextLine();
    }

    private static String getMemberPhoneNumber() {
        System.out.print("Enter your Phone Number: \n");
        return input.nextLine();
    }
}
