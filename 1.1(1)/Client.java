import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Addition stub = (Addition) Naming.lookup("rmi://localhost:1099/AddService");

            System.out.print("Enter first number: ");
            int a = sc.nextInt();

            System.out.print("Enter second number: ");
            int b = sc.nextInt();

            int result = stub.add(a, b);

            System.out.println("Addition is: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.out.println("Client exception: " + e.toString());
        }
    }
}
