import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MilesKilometerServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7004), 0);

            server.createContext("/convert", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                double miles = Double.parseDouble(query.get("miles"));
                double kilometers = miles * 1.60934;

                String response = "Kilometers: " + kilometers;
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Miles to Kilometer Web Service started on port 7004.");
        } catch (IOException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }

    static Map<String, String> getQuery(String query) {
        Map<String, String> values = new HashMap<>();
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            values.put(parts[0], parts[1]);
        }
        return values;
    }
}
