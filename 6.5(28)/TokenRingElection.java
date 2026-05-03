import java.util.Scanner;

public class TokenRingElection {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] processes = new int[n];
        System.out.println("Enter process ids:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        System.out.print("Enter process id which starts election: ");
        int initiator = sc.nextInt();
        int maxId = initiator;
        int startIndex = -1;

        for (int i = 0; i < n; i++) {
            if (processes[i] == initiator) {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            System.out.println("Initiator process not found.");
            return;
        }

        System.out.println();
        System.out.println("Process " + initiator + " starts election.");

        int current = startIndex;
        do {
            int next = (current + 1) % n;

            System.out.println("Election message sent from Process "
                    + processes[current] + " to Process " + processes[next]);

            if (processes[next] > maxId) {
                maxId = processes[next];
            }

            current = next;
        } while (current != startIndex);

        System.out.println();
        System.out.println("Election message returned to initiator.");
        System.out.println("Process " + maxId + " is selected as new coordinator.");

        current = startIndex;
        do {
            int next = (current + 1) % n;
            System.out.println("Coordinator message sent from Process "
                    + processes[current] + " to Process " + processes[next]);
            current = next;
        } while (current != startIndex);

        System.out.println();
        System.out.println("New Coordinator is Process " + maxId);
        }
    }
}
