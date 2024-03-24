package gym.client.server.app;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TCPServer {

    static int nextMemberNumber = 1;
    static ArrayList<Member> allMembers = new ArrayList();
    static int SERVER_PORT = 1105;
    static int INTERVAL = 2000;

    public static void main(String args[]) {
        // Initialise timer for Object file sync with txt file.
        Timer timer = new Timer();
        timer.schedule(new SyncObjectFile(), INTERVAL, INTERVAL);

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
    
    @Override
    public void run() {
        fileName = "memberlist.txt";
        objectFileName = "memberlistObject";
        TCPServer.allMembers.clear();
        readTextFileData();            
        writeMembersToObjectFile();
    }
    
    // Read contents of txt file and save to allMembers List
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