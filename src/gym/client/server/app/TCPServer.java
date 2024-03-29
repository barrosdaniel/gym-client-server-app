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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The TCPServer class is a TCP server for the gym client/server application.
 * This server listens for client connections, receives member details from
 * clients, and writes the received data to a text file. It also periodically
 * synchronises the data from the text file to an object file.
 *
 * This class implements the server-side functionality of the gym client/server
 * application.
 *
 * @author Daniel Barros
 * @version 1.0
 */
public class TCPServer {

    // Declare and initialise constants    
    private static int SERVER_PORT = 1105;
    private static int INTERVAL = 2000;

    // Declare class members
    static int nextMemberNumber;
    static ArrayList<Member> allMembers;

    /**
     * The main method serves as the entry point for the TCP server. It
     * initialises a timer for periodically syncing data from the text file to
     * the object file. It also listens for client connections and creates a new
     * threaded connection for each client request.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String args[]) {
        nextMemberNumber = 1;
        allMembers = new ArrayList();

        // Initialise timer for Object file sync with txt file.
        Timer timer = new Timer();
        timer.schedule(new SyncObjectFile(), INTERVAL, INTERVAL);

        // Listen to client requests and create a new threaded connection for 
        // each client request.
        try {
            ServerSocket listenSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}

class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    String fileName;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            fileName = "memberlist.txt";
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    /**
     * The run method represents the functionality executed by each thread when
     * handling a client connection. It receives data from the client, writes it
     * to a text file, sends a response to the client, and increments the server
     * member counter.
     */
    @Override
    public void run() {
        try {
            // Receive message from client
            System.out.println("Receiving data from client: "
                    + TCPServer.nextMemberNumber);
            String data = in.readUTF();

            // Write message data to txt file
            if (data != null) {
                writeMemberDataToFile(data);
            }

            // Send reponse to client
            out.writeUTF("Save Data of the member number");

            // Add to server member counter
            TCPServer.nextMemberNumber += 1;
        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Writes the received member data to the text file in the pre-defined
     * format.
     *
     * @param data the member data to write to the file
     */
    private void writeMemberDataToFile(String data) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write(data + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SyncObjectFile extends TimerTask {

    String fileName;
    String objectFileName;

    /**
     * The run method introduces the functionality executed by the timer task to
     * synchronise data from the text file to the object file.
     */
    @Override
    public void run() {
        fileName = "memberlist.txt";
        objectFileName = "memberlistObject";
        TCPServer.allMembers.clear();
        readTextFileData();
        writeMembersToObjectFile();
    }

    /**
     * Reads the contents of the text file and saves them to the list of all
     * members.
     */
    public void readTextFileData() {
        String memberLine;
        Member member;
        String[] memberData;

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while ((memberLine = br.readLine()) != null) {
                memberData = memberLine.split(":");
                member = new Member();
                member.setFirstName(memberData[0]);
                member.setLastName(memberData[1]);
                member.setAddress(memberData[2]);
                member.setPhoneNumber(memberData[3]);
                TCPServer.allMembers.add(member);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the list of all members data to the object file.
     */
    public void writeMembersToObjectFile() {
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream(objectFileName, false);
            out = new ObjectOutputStream(fos);
            for (Member member : TCPServer.allMembers) {
                out.writeObject(member);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
