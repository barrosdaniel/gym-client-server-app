package gym.client.server.app;

import java.net.*;
import java.io.*;

public class TCPClient {

    public static void main(String args[]) {
        System.out.println("LOG: TCPClient started.");
        
        // arguments supply message and hostname of destination
        // if running from a command prompt
        Socket s = null;
        String hostName = "localhost";
        String message = "Hello from Client";
        try {
            int serverPort = 7896;

            s = new Socket(hostName, serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(message);
            String data = in.readUTF();
            System.out.println("Message Received From Server: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (s != null)
                try {
                s.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }
}
