import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TokenRingLeaderElection {

    static int processId;
    static int myPort;
    static String nextHost;
    static int nextPort;

    public static void main(String[] args) throws Exception {

        if (args.length < 4) {

            System.out.println(
                    "Usage: java TokenRingLeaderElection <id> <myPort> <nextHost> <nextPort>");

            return;
        }

        processId = Integer.parseInt(args[0]);
        myPort = Integer.parseInt(args[1]);
        nextHost = args[2];
        nextPort = Integer.parseInt(args[3]);

        new Thread(() -> receiveMessages()).start();

        Thread.sleep(2000);

        Scanner sc = new Scanner(System.in);

        System.out.print("Start election? (yes/no): ");

        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {

            String token = "ELECTION:" + processId;

            System.out.println("\nProcess "
                    + processId
                    + " starts ELECTION");

            sendMessage(token);
        }
    }

    static void receiveMessages() {

        try (ServerSocket serverSocket =
                     new ServerSocket(myPort)) {

            System.out.println("Process "
                    + processId
                    + " listening on port "
                    + myPort);

            while (true) {

                Socket socket = serverSocket.accept();

                DataInputStream dis =
                        new DataInputStream(socket.getInputStream());

                String message = dis.readUTF();

                if (message.startsWith("ELECTION:")) {

                    System.out.println("\nProcess "
                            + processId
                            + " received TOKEN -> "
                            + message);

                    String ids = message.substring(9);

                    List<Integer> list = new ArrayList<>();

                    for (String s : ids.split(",")) {

                        list.add(Integer.parseInt(s));
                    }

                    if (list.contains(processId)) {

                        int coordinator =
                                Collections.max(list);

                        System.out.println("\nProcess "
                                + coordinator
                                + " becomes COORDINATOR");

                        String coordinatorMessage =
                                "COORDINATOR:"
                                        + coordinator
                                        + ":"
                                        + processId;

                        sendMessage(coordinatorMessage);

                    } else {

                        message += "," + processId;

                        System.out.println("Process "
                                + processId
                                + " passes TOKEN");

                        sendMessage(message);
                    }
                }

                else if (message.startsWith("COORDINATOR:")) {

                    String[] parts = message.split(":");

                    int coordinator =
                            Integer.parseInt(parts[1]);

                    int initiator =
                            Integer.parseInt(parts[2]);

                    System.out.println("\nProcess "
                            + processId
                            + " knows coordinator is Process "
                            + coordinator);

                    if (processId != initiator) {

                        sendMessage(message);

                    } else {

                        System.out.println(
                                "\nCoordinator message completed full ring.");
                    }
                }

                socket.close();
            }

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }

    static void sendMessage(String message) {

        try {

            Socket socket =
                    new Socket(nextHost, nextPort);

            DataOutputStream dos =
                    new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(message);

            socket.close();

        } catch (Exception e) {

            System.out.println(
                    "Cannot connect to next process.");
        }
    }
}
/*
========================================================
6.5 TOKEN RING LEADER ELECTION USING SOCKETS
========================================================

AIM:
Perform Leader Election using Token Ring Algorithm
using real socket communication and print messages
exchanged between processes.

========================================================
HOW IT WORKS
========================================================

1. Each process runs in separate terminal/machine.
2. Every process acts as:
      - Server  -> Receives TOKEN
      - Client  -> Sends TOKEN
3. Processes form logical ring topology.
4. Election TOKEN circulates in ring.
5. Highest Process ID becomes COORDINATOR.
6. Coordinator message circulates to all processes.

========================================================
HOW TO RUN (2 TERMINALS SAME PC)
========================================================

STEP 1:
Compile

javac TokenRingLeaderElection.java

--------------------------------------------------------

STEP 2:
Open TERMINAL 1

java TokenRingLeaderElection 1 5001 localhost 5002

Type:
yes

--------------------------------------------------------

STEP 3:
Open TERMINAL 2

java TokenRingLeaderElection 2 5002 localhost 5001

DO NOT TYPE ANYTHING.

// IF more to run
For 4 Terminals

Ring becomes:

1 → 2 → 3 → 4 → 1

Commands:

T1
java TokenRingLeaderElection 1 5001 localhost 5002
T2
java TokenRingLeaderElection 2 5002 localhost 5003
T3
java TokenRingLeaderElection 3 5003 localhost 5004
T4
java TokenRingLeaderElection 4 5004 localhost 5001

========================================================
HOW TO RUN (DIFFERENT MACHINES)
========================================================

Machine 1:
java TokenRingLeaderElection 1 5001 192.168.1.11 5002

Machine 2:
java TokenRingLeaderElection 2 5002 192.168.1.10 5001

========================================================
*/
