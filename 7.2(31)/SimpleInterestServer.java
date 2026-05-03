import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimpleInterestServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7002), 0);

            server.createContext("/interest", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                double principal = Double.parseDouble(query.get("principal"));
                double rate = Double.parseDouble(query.get("rate"));
                double time = Double.parseDouble(query.get("time"));
                double interest = (principal * rate * time) / 100;

                String response = "Simple interest: " + interest;
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Simple Interest Web Service started on port 7002.");
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
