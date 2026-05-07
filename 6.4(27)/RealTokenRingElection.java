import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RealTokenRingElection {

    static int processId;
    static int myPort;
    static String nextHost;
    static int nextPort;

    public static void main(String[] args) throws Exception {

        if (args.length < 4) {

            System.out.println(
                    "Usage: java RealTokenRingElection <id> <myPort> <nextHost> <nextPort>");

            return;
        }

        processId = Integer.parseInt(args[0]);
        myPort = Integer.parseInt(args[1]);
        nextHost = args[2];
        nextPort = Integer.parseInt(args[3]);

        // START SERVER THREAD
        new Thread(() -> receiveMessages()).start();

        Thread.sleep(2000);

        Scanner sc = new Scanner(System.in);

        System.out.print("Start election? (yes/no): ");
        String choice = sc.nextLine();

        // ONLY ONE PROCESS SHOULD START ELECTION
        if (choice.equalsIgnoreCase("yes")) {

            String token = "ELECTION:" + processId;

            System.out.println("\nProcess "
                    + processId
                    + " starts election");

            sendMessage(token);
        }

        sc.close();
    }

    // RECEIVE MESSAGES
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

                // ELECTION MESSAGE
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

                    // TOKEN RETURNED TO INITIATOR
                    if (list.contains(processId)) {

                        int coordinator =
                                Collections.max(list);

                        System.out.println("\nProcess "
                                + coordinator
                                + " becomes COORDINATOR");

                        // SEND COORDINATOR MESSAGE
                        String coordinatorMsg =
                                "COORDINATOR:"
                                        + coordinator
                                        + ":"
                                        + processId;

                        sendMessage(coordinatorMsg);

                    } else {

                        // ADD OWN ID
                        message += "," + processId;

                        System.out.println("Process "
                                + processId
                                + " passes TOKEN");

                        sendMessage(message);
                    }
                }

                // COORDINATOR MESSAGE
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

                    // STOP WHEN MESSAGE RETURNS
                    // TO INITIATOR AGAIN
                    if (processId != initiator) {

                        sendMessage(message);

                    } else {

                        System.out.println("\nCoordinator message completed full ring.");
                    }
                }

                socket.close();
            }

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }

    // SEND MESSAGE
    static void sendMessage(String msg) {

        try {

            Socket socket =
                    new Socket(nextHost, nextPort);

            DataOutputStream dos =
                    new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(msg);

            socket.close();

        } catch (Exception e) {

            System.out.println("Cannot connect to next process.");
        }
    }
}

/*
========================================================
REAL TOKEN RING LEADER ELECTION USING SOCKETS
========================================================

AIM:
Implement Token Ring Leader Election Algorithm using
real socket communication between processes.

========================================================
HOW IT WORKS
========================================================

1. Each process runs in separate terminal/machine.
2. Every process acts as:
      - Server  -> Receives TOKEN
      - Client  -> Passes TOKEN
3. Processes form a logical ring.
4. Election TOKEN circulates in ring.
5. Highest Process ID becomes COORDINATOR.
6. Coordinator message circulates to all processes.

========================================================
RING STRUCTURE EXAMPLE
========================================================

Process 1 ---> Process 2 ---> Process 1

Ports:
P1 -> 5001
P2 -> 5002

========================================================
HOW TO RUN (SAME PC - 2 TERMINALS)
========================================================

--------------------------------------------------------
STEP 1 : OPEN TERMINAL 1
--------------------------------------------------------

cd "D:\LP-V\6.4(27)"

Compile:
javac RealTokenRingElection.java

Run:
java RealTokenRingElection 1 5001 localhost 5002

When asked:
Start election? (yes/no):

Type:
yes

--------------------------------------------------------
STEP 2 : OPEN TERMINAL 2
--------------------------------------------------------

cd "D:\LP-V\6.4(27)"

Run:
java RealTokenRingElection 2 5002 localhost 5001

DO NOT TYPE ANYTHING.
Keep terminal running.

========================================================
HOW TO RUN (DIFFERENT MACHINES)
========================================================

Example:

Machine 1 IP:
192.168.1.10

Machine 2 IP:
192.168.1.11

--------------------------------------------------------

Machine 1:
java RealTokenRingElection 1 5001 192.168.1.11 5002

Machine 2:
java RealTokenRingElection 2 5002 192.168.1.10 5001

========================================================
ARGUMENT FORMAT
========================================================

java RealTokenRingElection
     <ProcessID>
     <MyPort>
     <NextMachineIP>
     <NextProcessPort>

========================================================
SAMPLE EXECUTION
========================================================

---------------- TERMINAL 1 ----------------

Process 1 listening on port 5001

Start election? yes

Process 1 starts election

Process 1 received TOKEN -> ELECTION:1,2

Process 2 becomes COORDINATOR

Process 1 knows coordinator is Process 2

Coordinator message completed full ring.

--------------------------------------------------------

---------------- TERMINAL 2 ----------------

Process 2 listening on port 5002

Process 2 received TOKEN -> ELECTION:1

Process 2 passes TOKEN

Process 2 knows coordinator is Process 2

========================================================
WORKING EXPLANATION
========================================================

1. Process 1 creates ELECTION TOKEN.
2. TOKEN moves to Process 2.
3. Process 2 adds its ID into TOKEN.
4. TOKEN returns to Process 1.
5. Highest ID selected as COORDINATOR.
6. COORDINATOR message circulates once.
7. After full ring completion, election stops.

========================================================
RESULT
========================================================

Token Ring Leader Election Algorithm implemented
successfully using real socket communication
between processes.

========================================================
*/