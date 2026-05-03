import java.util.Scanner;

public class BullyElectionMessages {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] processes = new int[n];
        System.out.println("Enter process ids:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        System.out.print("Enter failed coordinator id: ");
        int failedCoordinator = sc.nextInt();

        System.out.print("Enter process starting election: ");
        int initiator = sc.nextInt();

        System.out.println("Coordinator " + failedCoordinator + " failed.");
        System.out.println("Process " + initiator + " starts bully election.");

        int newCoordinator = initiator;
        for (int process : processes) {
            if (process > initiator && process != failedCoordinator) {
                System.out.println("Process " + initiator + " sends ELECTION to " + process);
                System.out.println("Process " + process + " sends OK to " + initiator);

                if (process > newCoordinator) {
                    newCoordinator = process;
                }
            }
        }

        for (int process : processes) {
            if (process != newCoordinator && process != failedCoordinator) {
                System.out.println("Process " + newCoordinator + " sends COORDINATOR to " + process);
            }
        }

        System.out.println("New coordinator is process " + newCoordinator);
        }
    }
}
