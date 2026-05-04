import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TimeDaemonClient {
    public static void main(String[] args) {
        try {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            try (Socket socket = new Socket(serverIP, 5001)) {
                long localTime = System.currentTimeMillis();

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(localTime);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long synchronizedTime = dis.readLong();

                System.out.println("Local time: " + localTime);
                System.out.println("Synchronized time received from daemon: " + synchronizedTime);
                System.out.println("Time adjustment: " + (synchronizedTime - localTime));
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
TIME DAEMON SYNC - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile in this folder: javac *.java
3. Run daemon server first: java TimeDaemonServer
4. Run two clients in separate terminals: java TimeDaemonClient
5. No keyboard input is needed; clients send local time automatically.
6. Output shows synchronized daemon time and adjustment.
*/
