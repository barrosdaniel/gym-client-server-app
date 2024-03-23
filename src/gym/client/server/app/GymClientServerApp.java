package gym.client.server.app;

public class GymClientServerApp {
    private static int nextMemberNumber = 1;

    public static void main(String[] args) {
        System.out.println("LOG: Application started.");
        
        // Start application servers
        TCPServer.main(args);
    }

    public static int getNextMemberNumber() {
        return nextMemberNumber;
    }

    public void setNextMemberNumber(int nextMemberNumber) {
        this.nextMemberNumber = nextMemberNumber;
    }
    
    public static void increaseNextMemberNumber() {
        nextMemberNumber += 1;
    }
}
