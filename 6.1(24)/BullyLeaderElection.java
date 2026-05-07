import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BullyLeaderElection {

    static int processId;
    static int myPort;

    static int[] ports = {5001, 5002, 5003};

    static boolean coordinatorDeclared = false;

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {

            System.out.println(
                    "Usage: java BullyLeaderElection <id> <port>");

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
6.1 BULLY ALGORITHM LEADER ELECTION USING SOCKETS
========================================================

AIM:
Perform Leader Election using Bully Algorithm
using real socket communication and print
messages exchanged between processes.

========================================================
HOW TO RUN
========================================================

STEP 1:
Compile

javac BullyLeaderElection.java

========================================================
RUN ON SAME PC (3 TERMINALS)
========================================================

--------------- TERMINAL 1 ----------------

java BullyLeaderElection 1 5001 localhost 5002

--------------------------------------------------------

--------------- TERMINAL 2 ----------------

java BullyLeaderElection 2 5002 localhost 5003

--------------------------------------------------------

--------------- TERMINAL 3 ----------------

java BullyLeaderElection 3 5003 localhost 5001

========================================================
START ELECTION
========================================================

ONLY ONE PROCESS SHOULD TYPE:

yes

========================================================
*/