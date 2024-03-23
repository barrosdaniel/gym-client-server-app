package gym.client.server.app;

import java.net.*;
import java.io.*;

public class TCPServer {
    
    static int nextMemberNumber = 1;

    public static void main(String args[]) {
        System.out.println("LOG: TCP Server started.");
        int SERVER_PORT = 1105;
        try {
            ServerSocket listenSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                System.out.printf("\nServer waiting on: %d for client from %d ",
                        listenSocket.getLocalPort(), clientSocket.getPort());
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
    String filename;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            filename = "memberlist.txt";
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            // Receive message from client
            System.out.println("Receiving data from client: " 
                    + TCPServer.nextMemberNumber);
            String data = in.readUTF();

            // Write message data to txt file
            if (data != null) {
//                System.out.println(data);
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

    private void writeMemberDataToFile(String data) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write(data + "\n");
            System.out.println("Data received from client saved into " + filename);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
