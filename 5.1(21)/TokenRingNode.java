import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TokenRingNode {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TokenRingNode <0|1>");
            return;
        }

        int nodeId = Integer.parseInt(args[0]);

        if (nodeId == 0) {
            runNode0();
        } else {
            runNode1();
        }
    }

    static void runNode0() {
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Site 0 started with unique token.");
            enterCriticalSection(0);

            try (Socket sendSocket = new Socket("localhost", 6001)) {
                DataOutputStream dos = new DataOutputStream(sendSocket.getOutputStream());
                dos.writeUTF("TOKEN");
                System.out.println("Site 0 passed token to Site 1.");
            }

            try (Socket receiveSocket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
                String token = dis.readUTF();
                System.out.println("Site 0 received " + token + " back from Site 1.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Node 0 exception: " + e.toString());
        }
    }

    static void runNode1() {
        try (ServerSocket serverSocket = new ServerSocket(6001)) {
            System.out.println("Site 1 waiting for token...");

            try (Socket receiveSocket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
                String token = dis.readUTF();
                System.out.println("Site 1 received " + token + ".");
            }

            enterCriticalSection(1);

            try (Socket sendSocket = new Socket("localhost", 6000)) {
                DataOutputStream dos = new DataOutputStream(sendSocket.getOutputStream());
                dos.writeUTF("TOKEN");
                System.out.println("Site 1 passed token to Site 0.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Node 1 exception: " + e.toString());
        }
    }

    static void enterCriticalSection(int site) throws InterruptedException {
        System.out.println("Site " + site + " has token.");
        System.out.println("Site " + site + " enters critical section.");
        Thread.sleep(1000);
        System.out.println("Site " + site + " exits critical section.");
    }
}
