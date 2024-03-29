package gym.client.server.app;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPServer {
    
    // Initialise constants
    private static int SERVER_PORT = 2205;
    private static String OBJECT_FILE_NAME = "memberlistObject";
    
    // Declare class members
    private static DatagramSocket socket;
    private static StringBuilder sb;
    private static String messageString;
    private static byte[] messageByte;
    private static ArrayList<Member> allMembersList;

    public static void main(String args[]) {
        allMembersList = new ArrayList();
        
        try {
            // Create socket
            socket = new DatagramSocket(SERVER_PORT);
            
            // Prepare buffer to receive client request
            byte[] buffer = new byte[1000];
            
            while (true) {
                // Listen for client request
                DatagramPacket request = new DatagramPacket(buffer, 
                        buffer.length);
                socket.receive(request);
                
                // Print client request
                System.out.println("Client Request: " + new String(
                        request.getData(), 0, request.getLength()));
                
                // Get object file data
                getObjectFileData();

                prepareMessage();
                
                // Prepare response packet
                DatagramPacket reply = new DatagramPacket(messageByte, 
                        messageByte.length, request.getAddress(), 
                        request.getPort());
                
                // Send response to client
                socket.send(reply);
                
                // Clear members list after sending response. This is needed to
                // avoid duplication in the result set when a new client sends
                // a request.
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
	} catch(IOException e) {
	    e.printStackTrace();
	} catch(ClassNotFoundException e){
	    e.printStackTrace();
	}
    }
}
