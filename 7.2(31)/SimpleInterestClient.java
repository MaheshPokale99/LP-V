import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class SimpleInterestClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter principal: ");
            double principal = sc.nextDouble();

            System.out.print("Enter rate: ");
            double rate = sc.nextDouble();

            System.out.print("Enter time: ");
            double time = sc.nextDouble();

            String address = "http://" + serverIP + ":7002/interest?principal=" + principal
                    + "&rate=" + rate + "&time=" + time;
            HttpURLConnection connection = (HttpURLConnection) URI.create(address).toURL().openConnection();

            try (Scanner responseScanner = new Scanner(connection.getInputStream())) {
                System.out.println(responseScanner.nextLine());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
