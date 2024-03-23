package gym.client.server.app;

import java.net.*;
import java.io.*;

public class TCPClient {

    public static void main(String args[]) {
        System.out.println("LOG: TCP Client started.");

        int SERVER_PORT = 1105;
        String HOST_NAME = "localhost";
        Socket socket = null;
        String message = "Hello from Client";
        try {
            socket = new Socket(HOST_NAME, SERVER_PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
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
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}
