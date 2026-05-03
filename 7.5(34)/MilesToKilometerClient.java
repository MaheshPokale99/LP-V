import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class MilesToKilometerClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter miles: ");
            double miles = sc.nextDouble();

            String address = "http://" + serverIP + ":7005/miles?miles=" + miles;
            HttpURLConnection connection = (HttpURLConnection) URI.create(address).toURL().openConnection();

            try (Scanner responseScanner = new Scanner(connection.getInputStream())) {
                System.out.println(responseScanner.nextLine());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
