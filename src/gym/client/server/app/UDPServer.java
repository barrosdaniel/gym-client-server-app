package gym.client.server.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    
    private static int SERVER_PORT = 2205;
    private static DatagramSocket socket;

    public static void main(String args[]) {
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
                
                // Prepare response
                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),
                        request.getAddress(), request.getPort());
                
                // Send response to client
                socket.send(reply);
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
}
