import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class SimpleCalculatorClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter first number: ");
            double a = sc.nextDouble();

            System.out.print("Enter second number: ");
            double b = sc.nextDouble();

            System.out.print("Enter operation add/sub/mul/div: ");
            String operation = sc.next();

            String address = "http://" + serverIP + ":7001/calculator?a=" + a
                    + "&b=" + b + "&operation=" + operation;
            HttpURLConnection connection = (HttpURLConnection) URI.create(address).toURL().openConnection();

            try (Scanner responseScanner = new Scanner(connection.getInputStream())) {
                System.out.println(responseScanner.nextLine());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
