import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BullyTimeComplexity {

    static int processId;
    static int myPort;

    static int[] ports = {5001, 5002, 5003};

    static boolean coordinatorDeclared = false;

    static int messageCount = 0;

    public static void main(String[] args) throws Exception {

        /*
        TERMINAL 1:
        java BullyTimeComplexity 1 5001

        TERMINAL 2:
        java BullyTimeComplexity 2 5002

        TERMINAL 3:
        java BullyTimeComplexity 3 5003
        */

        if (args.length < 2) {

            System.out.println(
                    "Usage: java BullyTimeComplexity <id> <port>");

            return;
        }

        processId = Integer.parseInt(args[0]);
        myPort = Integer.parseInt(args[1]);

        new Thread(() -> receiveMessages()).start();

        Thread.sleep(2000);

        Scanner sc = new Scanner(System.in);

        System.out.print("Start election? (yes/no): ");

        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {

            startElection();
        }
    }

    static void startElection() {

        System.out.println(
                "\nProcess "
                        + processId
                        + " starts ELECTION");

        boolean higherExists = false;

        for (int i = processId; i < ports.length; i++) {

            int targetPort = ports[i];

            if (targetPort != myPort) {

                higherExists = true;

                sendMessage(
                        targetPort,
                        "ELECTION:" + processId);

                System.out.println(
                        "Process "
                                + processId
                                + " sends ELECTION to Process "
                                + (i + 1));

                messageCount++;
            }
        }

        if (!higherExists) {

            becomeCoordinator();
        }
    }

    static void receiveMessages() {

        try (ServerSocket serverSocket =
                     new ServerSocket(myPort)) {

            System.out.println(
                    "Process "
                            + processId
                            + " listening on port "
                            + myPort);

            while (true) {

                Socket socket = serverSocket.accept();

                DataInputStream dis =
                        new DataInputStream(
                                socket.getInputStream());

                String message = dis.readUTF();

                if (message.startsWith("ELECTION:")) {

                    int sender =
                            Integer.parseInt(
                                    message.split(":")[1]);

                    System.out.println(
                            "\nProcess "
                                    + processId
                                    + " received ELECTION from Process "
                                    + sender);

                    sendMessage(
                            ports[sender - 1],
                            "OK:" + processId);

                    System.out.println(
                            "Process "
                                    + processId
                                    + " sends OK to Process "
                                    + sender);

                    messageCount++;

                    startElection();
                }

                else if (message.startsWith("OK:")) {

                    int responder =
                            Integer.parseInt(
                                    message.split(":")[1]);

                    System.out.println(
                            "\nProcess "
                                    + processId
                                    + " received OK from Process "
                                    + responder);
                }

                else if (message.startsWith("COORDINATOR:")) {

                    int coordinator =
                            Integer.parseInt(
                                    message.split(":")[1]);

                    System.out.println(
                            "\nProcess "
                                    + processId
                                    + " knows coordinator is Process "
                                    + coordinator);

                    System.out.println(
                            "Total messages exchanged till now = "
                                    + messageCount);

                    System.out.println(
                            "Time Complexity = O(n²)");
                }

                socket.close();
            }

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }

    static void becomeCoordinator() {

        if (coordinatorDeclared)
            return;

        coordinatorDeclared = true;

        System.out.println(
                "\nProcess "
                        + processId
                        + " becomes COORDINATOR");

        for (int port : ports) {

            if (port != myPort) {

                sendMessage(
                        port,
                        "COORDINATOR:" + processId);

                messageCount++;
            }
        }
    }

    static void sendMessage(
            int port,
            String message) {

        try {

            Socket socket =
                    new Socket("localhost", port);

            DataOutputStream dos =
                    new DataOutputStream(
                            socket.getOutputStream());

            dos.writeUTF(message);

            socket.close();

        } catch (Exception e) {

            System.out.println(
                    "Cannot connect to process on port "
                            + port);
        }
    }
}

/*
========================================================
6.3 BULLY ALGORITHM LEADER ELECTION
========================================================

AIM:
Perform Leader Election using Bully Algorithm
and demonstrate time complexity of the algorithm.

========================================================
CHECK JAVA INSTALLATION
========================================================

java -version
javac -version

========================================================
COMPILE PROGRAM
========================================================

javac BullyTimeComplexity.java

========================================================
RUN PROGRAM IN MULTIPLE TERMINALS
========================================================

--------------- TERMINAL 1 ----------------

java BullyTimeComplexity 1 5001

--------------------------------------------------------

--------------- TERMINAL 2 ----------------

java BullyTimeComplexity 2 5002

--------------------------------------------------------

--------------- TERMINAL 3 ----------------

java BullyTimeComplexity 3 5003

========================================================
INPUT
========================================================

Only ONE process should type:

yes

Other terminals:
- Do not type anything
- Keep terminals running

========================================================
WORKING
========================================================

1. One process starts election.
2. Election messages are sent to higher-ID processes.
3. Higher processes send OK messages.
4. Highest active process becomes COORDINATOR.
5. Coordinator message sent to all processes.
6. Total exchanged messages counted.
7. Time complexity displayed.

========================================================
SAMPLE EXECUTION
========================================================

--------------- TERMINAL 1 ----------------

Process 1 listening on port 5001

Start election? (yes/no): yes

Process 1 starts ELECTION

Process 1 sends ELECTION to Process 2

Process 1 sends ELECTION to Process 3

Process 1 received OK from Process 2

Process 1 received OK from Process 3

Process 1 knows coordinator is Process 3

Total messages exchanged till now = 4

Time Complexity = O(n²)

--------------------------------------------------------

--------------- TERMINAL 2 ----------------

Process 2 listening on port 5002

Process 2 received ELECTION from Process 1

Process 2 sends OK to Process 1

Process 2 starts ELECTION

Process 2 sends ELECTION to Process 3

Process 2 received OK from Process 3

Process 2 knows coordinator is Process 3

--------------------------------------------------------

--------------- TERMINAL 3 ----------------

Process 3 listening on port 5003

Process 3 received ELECTION from Process 1

Process 3 sends OK to Process 1

Process 3 received ELECTION from Process 2

Process 3 sends OK to Process 2

Process 3 starts ELECTION

Process 3 becomes COORDINATOR

========================================================
TIME COMPLEXITY
========================================================

Worst Case Time Complexity:

O(n²)

Reason:
- Every process may send election messages
- Higher processes send OK messages
- Coordinator broadcasts coordinator message

========================================================
RESULT
========================================================

Leader Election using Bully Algorithm implemented
successfully using socket communication and
time complexity demonstrated.

========================================================
*/