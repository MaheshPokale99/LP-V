import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Scanner;

public class HelloUserClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter user name: ");
            String name = sc.nextLine();

            String address = "http://" + serverIP + ":7003/hello?name="
                    + URLEncoder.encode(name, "UTF-8");
            HttpURLConnection connection = (HttpURLConnection) URI.create(address).toURL().openConnection();

            try (Scanner responseScanner = new Scanner(connection.getInputStream())) {
                System.out.println(responseScanner.nextLine());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
