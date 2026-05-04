import java.util.*;

public class BullyElection {

    static int[] processes;
    static int failed;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        processes = new int[n];

        System.out.println("Enter process ids:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        Arrays.sort(processes);

        System.out.print("Enter failed coordinator id: ");
        failed = sc.nextInt();

        System.out.print("Enter election initiator id: ");
        int initiator = sc.nextInt();

        System.out.println("\nCoordinator " + failed + " failed.");

        election(initiator);

        sc.close();
    }

    static void election(int initiator) {
        System.out.println("\nProcess " + initiator + " starts election");

        for (int p : processes) {
            if (p > initiator && p != failed) {

                System.out.println("Election message from " + initiator + " to " + p);
                System.out.println("OK message from " + p + " to " + initiator);

                election(p);
                return;
            }
        }

        System.out.println("\nProcess " + initiator + " becomes COORDINATOR");

        for (int p : processes) {
            if (p != initiator && p != failed) {
                System.out.println("Coordinator message from " + initiator + " to " + p);
            }
        }
    }
}

/*
1. Check Java installation:
   java -version
   javac -version

2. Go to the folder where the file is saved:
   cd path_to_your_folder

   Example:
   cd Desktop
   cd JavaPrograms

3. Compile the program:
   javac BullyElection.java
   (or BullyElectionMessages.java for message version)

   👉 If no error → compilation successful

4. Run the program:
   java BullyElection
   (or java BullyElectionMessages)

5. Enter inputs when prompted:
   - Enter number of processes
   - Enter process IDs (if required)
   - Enter failed coordinator ID
   - Enter election initiator ID

6. Program will display:
   - ELECTION messages
   - OK messages
   - COORDINATOR messages
   - (For 6.3) Total message count & time complexity

--------------------------------------------------

🔹 SAMPLE INPUT:

Enter number of processes: 5
Enter process IDs:
1 2 3 4 5
Enter failed coordinator ID: 5
Enter election initiator ID: 2

--------------------------------------------------

🔹 SAMPLE OUTPUT:

Coordinator 5 failed.

Process 2 starts election
Process 2 sends ELECTION to 3
Process 3 sends OK to 2

Process 3 starts election
Process 3 sends ELECTION to 4
Process 4 sends OK to 3

Process 4 starts election

Process 4 becomes COORDINATOR
Process 4 sends COORDINATOR to 1
Process 4 sends COORDINATOR to 2
Process 4 sends COORDINATOR to 3

New coordinator is process 4

--------------------------------------------------

🔹 (Only for 6.3 – Complexity Version)

Total messages exchanged = X
Time Complexity = O(n^2)

--------------------------------------------------

NOTE:
- Highest active process becomes coordinator
- Failed coordinator is excluded
- Bully Algorithm uses:
  • ELECTION messages
  • OK messages
  • COORDINATOR messages
*/
