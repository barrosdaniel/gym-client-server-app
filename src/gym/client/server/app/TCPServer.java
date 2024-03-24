package gym.client.server.app;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class TCPServer {
    
    static int nextMemberNumber = 1;
    static ArrayList<Member> allMembers = new ArrayList();

    public static void main(String args[]) {
        System.out.println("LOG: TCP Server started.");
        int SERVER_PORT = 1105;
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
                writeMemberDataToFile(data);
            }
            
            // TEMP: Save data to Object file. To be replaced by timer.
            syncObjectFile();
            for (Member m : TCPServer.allMembers) {
                System.out.println(m.toString());
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
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void syncObjectFile() {
        System.out.println("Synchronising data to " + filename);
        String memberLine;
        Member member;
        String[] memberData;
        
        // Read contents of txt file and save to allMembers List
        try {
            FileReader fr = new FileReader(filename);
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

        
        // Write allMembers List contents to object file
        
    }
}
