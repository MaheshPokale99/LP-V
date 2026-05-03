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
