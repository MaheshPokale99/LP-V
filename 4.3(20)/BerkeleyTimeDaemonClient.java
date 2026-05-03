import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BerkeleyTimeDaemonClient {
    public static void main(String[] args) {
        try {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            try (Socket socket = new Socket(serverIP, 5002)) {
                long localTime = System.currentTimeMillis();

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(localTime);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long synchronizedTime = dis.readLong();

                System.out.println("Local machine time: " + localTime);
                System.out.println("Synchronized time from daemon: " + synchronizedTime);
                System.out.println("Clock adjustment: " + (synchronizedTime - localTime));
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
