import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TokenRingSite {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TokenRingSite <0|1>");
            return;
        }

        int site = Integer.parseInt(args[0]);
        if (site == 0) {
            runSite0();
        } else {
            runSite1();
        }
    }

    static void runSite0() {
        try (ServerSocket serverSocket = new ServerSocket(6020)) {
            System.out.println("Site 0 starts token ring.");
            executeCriticalSection(0);
            passToken(6021, "Site 0 passes unique token to Site 1.");

            try (Socket socket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println("Site 0 received " + dis.readUTF() + ".");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Site 0 exception: " + e.toString());
        }
    }

    static void runSite1() {
        try (ServerSocket serverSocket = new ServerSocket(6021)) {
            System.out.println("Site 1 waits in token ring.");

            try (Socket socket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println("Site 1 received " + dis.readUTF() + ".");
            }

            executeCriticalSection(1);
            passToken(6020, "Site 1 passes unique token to Site 0.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Site 1 exception: " + e.toString());
        }
    }

    static void passToken(int port, String message) throws IOException {
        try (Socket socket = new Socket("localhost", port)) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("TOKEN");
            System.out.println(message);
        }
    }

    static void executeCriticalSection(int site) throws InterruptedException {
        System.out.println("Site " + site + " has unique token.");
        System.out.println("Site " + site + " is allowed to enter critical section.");
        Thread.sleep(1000);
        System.out.println("Site " + site + " leaves critical section.");
    }
}
