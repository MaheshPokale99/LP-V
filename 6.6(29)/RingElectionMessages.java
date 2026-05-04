import java.util.Scanner;

public class RingElectionMessages {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] ring = new int[n];
        System.out.println("Enter process ids in ring:");
        for (int i = 0; i < n; i++) {
            ring[i] = sc.nextInt();
        }

        System.out.print("Enter process id which starts election: ");
        int initiator = sc.nextInt();
        int startIndex = -1;

        for (int i = 0; i < n; i++) {
            if (ring[i] == initiator) {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            System.out.println("Initiator process not found.");
            return;
        }

        int leader = initiator;
        System.out.println("Process " + initiator + " starts election.");

        int current = startIndex;
        do {
            int next = (current + 1) % n;
            System.out.println("Election message sent from Process "
                    + ring[current] + " to Process " + ring[next]);

            if (ring[next] > leader) {
                leader = ring[next];
            }

            current = next;
        } while (current != startIndex);

        System.out.println("Election token returned to initiator.");
        System.out.println("Process " + leader + " is selected as new coordinator.");

        current = startIndex;
        do {
            int next = (current + 1) % n;
            System.out.println("Coordinator message sent from Process "
                    + ring[current] + " to Process " + ring[next]);
            current = next;
        } while (current != startIndex);

        System.out.println("New Coordinator is Process " + leader);
        }
    }
}

/*
RING ELECTION MESSAGES - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile in this folder: javac RingElectionMessages.java
3. Run: java RingElectionMessages
4. Input number of processes, then process IDs in ring.
5. Input process ID that starts election.
6. Output shows election token, coordinator messages, and leader.
*/
