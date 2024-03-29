/*
Central Queensland University
COIT13229 - Applied Distributed Systems (2024 Term 1)
Campus: External
Assignment 1 - Java Client/Server Application
Student ID: 12184305
Student Name: Daniel Barros
*/

/**
 * The GymClientServerApp class is the main entry point for the Java 
 * Client/Server Application project. This application is as a simple 
 * client-server system for managing gym operations. It is implemented by 
 * the main tasks split in the support classes TCPServer, TCPClient, UDPServer,
 * and UDPClient.
 * 
 * Usage:
 * To execute the application, run the main method of this class. Upon 
 * execution, the application will print a log message indicating that it has 
 * started.
 * 
 * @author Daniel Barros
 * @version 1.0
 */
package gym.client.server.app;

public class GymClientServerApp {
    /**
     * The main method is the entry point for the Java Client/Server 
     * Application.
     * 
     * It prints a log message indicating that the application has started. 
     * After compiling all other files in the project, the user has to run the
     * support classes individually to access all functionality.
     * 
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        System.out.println("LOG: Application started.");
    }
}
