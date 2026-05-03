import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimpleCalculatorServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7001), 0);

            server.createContext("/calculator", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                double a = Double.parseDouble(query.get("a"));
                double b = Double.parseDouble(query.get("b"));
                String operation = query.get("operation");

                double result;
                if (operation.equalsIgnoreCase("add")) {
                    result = a + b;
                } else if (operation.equalsIgnoreCase("sub")) {
                    result = a - b;
                } else if (operation.equalsIgnoreCase("mul")) {
                    result = a * b;
                } else if (operation.equalsIgnoreCase("div")) {
                    result = a / b;
                } else {
                    result = 0;
                }

                String response = "Calculator result: " + result;
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Simple Calculator Web Service started on port 7001.");
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
