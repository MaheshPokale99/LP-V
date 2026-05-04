import java.util.Scanner;

public class BullyElectionComplexity {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int messages = 0;

        System.out.println("Bully algorithm time complexity demonstration.");
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                messages++;
                System.out.println("Election message: " + i + " -> " + j);
            }
        }

        System.out.println("Total election messages: " + messages);
        System.out.println("Time complexity: O(n^2)");
        }
    }
}

/*
BULLY COMPLEXITY - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile in this folder: javac BullyElectionComplexity.java
3. Run: java BullyElectionComplexity
4. Input number of processes only.
5. No process IDs are needed in this program.
6. Output shows message count and O(n^2) complexity.
*/
