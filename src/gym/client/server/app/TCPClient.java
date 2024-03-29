package gym.client.server.app;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {

    // Declare and initialise server members
    private static String HOST_NAME = "localhost";
    private static int SERVER_PORT = 1105;
    private static Scanner input = new Scanner(System.in);
    private static Socket socket;
    private static int nextMemberNumber = 1;

    public static void main(String args[]) {
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
        String firstName;
        
        do {
            System.out.print("Enter your First Name: \n");
            firstName = input.nextLine().trim();
            if (firstName.isEmpty() || !(firstName.matches("[a-zA-Z]+"))) {
                System.out.println("Invalid input. First name must be "
                        + "alphanumeric.");
            }
        } while (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+"));

        return firstName;
    }

    private static String getMemberLastName() {
        String lastName;
        
        do {
            System.out.print("Enter your Last Name: \n");
            lastName = input.nextLine().trim();
            if (lastName.isEmpty() || !(lastName.matches("[a-zA-Z]+"))) {
                System.out.println("Invalid input. Last name must be "
                        + "alphanumeric.");
            }
        } while (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+"));

        return lastName;
    }

    private static String getMemberAddress() {
        String address;
        
        do {
            System.out.print("Enter your Address: \n");
            address = input.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("Invalid input. Address must not be empty.");
            }
        } while (address.isEmpty());

        return address;
    }

    private static String getMemberPhoneNumber() {
        String phoneNumber;
        
        do {
            System.out.print("Enter your Phone Number: \n");
            phoneNumber = input.nextLine().trim();
            if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d{10}")) {
                System.out.println("Invalid input. Phone number must be numeric"
                        + " and have 10 digits. No spaces allowed.");
            }
        } while (phoneNumber.isEmpty() || !phoneNumber.matches("\\d{10}"));

        return phoneNumber;
    }
}
