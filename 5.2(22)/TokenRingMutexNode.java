import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TokenRingMutexNode {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TokenRingMutexNode <0|1>");
            return;
        }

        int site = Integer.parseInt(args[0]);
        if (site == 0) {
            siteZero();
        } else {
            siteOne();
        }
    }

    static void siteZero() {
        try (ServerSocket serverSocket = new ServerSocket(6010)) {
            System.out.println("Site 0 owns unique token first.");
            criticalSection(0);

            sendToken(6011, "Site 0 sends token to Site 1.");

            try (Socket socket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println("Site 0 received " + dis.readUTF() + " from Site 1.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Site 0 exception: " + e.toString());
        }
    }

    static void siteOne() {
        try (ServerSocket serverSocket = new ServerSocket(6011)) {
            System.out.println("Site 1 waiting for token.");

            try (Socket socket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println("Site 1 received " + dis.readUTF() + " from Site 0.");
            }

            criticalSection(1);
            sendToken(6010, "Site 1 sends token back to Site 0.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Site 1 exception: " + e.toString());
        }
    }

    static void sendToken(int port, String message) throws IOException {
        try (Socket socket = new Socket("localhost", port)) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("TOKEN");
            System.out.println(message);
        }
    }

    static void criticalSection(int site) throws InterruptedException {
        System.out.println("Site " + site + " enters critical section because it has token.");
        Thread.sleep(1000);
        System.out.println("Site " + site + " exits critical section.");
    }
}
