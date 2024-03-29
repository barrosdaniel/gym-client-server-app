/*
Central Queensland University
COIT13229 - Applied Distributed Systems (2024 Term 1)
Campus: External
Assignment 1 - Java Client/Server Application
Student ID: 12184305
Student Name: Daniel Barros
 */
package gym.client.server.app;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * The TCPClient class is a TCP client application for the gym client/server
 * project.
 *
 * It communicates with the server to send member details (first name, last
 * name, address, and phone number). It prompts the user for member details and
 * sends them to the server via a TCP socket connection.
 *
 * This class implements the client-side functionality of the gym client/server
 * application.
 *
 * @author Daniel Barros
 * @version 1.0
 */
public class TCPClient {

    // Declare and initialise server members
    private static String HOST_NAME = "localhost";
    private static int SERVER_PORT = 1105;
    private static Scanner input = new Scanner(System.in);
    private static Socket socket;
    private static int nextMemberNumber = 1;

    /**
     * The main method is the entry point for the TCPClient routines. It prompts
     * the user for member details and sends them to the server via a TCP socket
     * connection.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String args[]) {
        // Loop through until client process is stopped
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

    /**
     * Prompt the user to enter the first name of the member. Validates the
     * input to ensure it not blank and has no numbers or special characters.
     *
     * @return the first name entered by the user
     */
    private static String getMemberFirstName() {
        String firstName;

        do {
            System.out.print("Enter your First Name: \n");
            firstName = input.nextLine().trim();
            if (firstName.isEmpty() || !(firstName.matches("[a-zA-Z]+"))) {
                System.out.println("Invalid input. First name must not be blank "
                        + "or have numeric or special characters.");
            }
        } while (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+"));

        return firstName;
    }

    /**
     * Prompt the user to enter the last name of the member. Validates the input
     * to ensure it not blank and has no numbers or special characters.
     *
     * @return the last name entered by the user
     */
    private static String getMemberLastName() {
        String lastName;

        do {
            System.out.print("Enter your Last Name: \n");
            lastName = input.nextLine().trim();
            if (lastName.isEmpty() || !(lastName.matches("[a-zA-Z]+"))) {
                System.out.println("Invalid input. Last name must not be blank "
                        + "or have numeric or special characters.");
            }
        } while (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+"));

        return lastName;
    }

    /**
     * Prompt the user to enter the address of the member. Validates the input
     * to ensure it is not blank.
     *
     * @return the address entered by the user
     */
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

    /**
     * Prompt the user to enter the phone number of the member. Validates the
     * input to ensure it is not blank, is numeric and has 10 digits.
     *
     * @return the phone number entered by the user
     */
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
