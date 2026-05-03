import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Subtract stub = (Subtract) Naming.lookup("rmi://localhost:1099/SubtractService");

            System.out.print("Enter first number: ");
            int a = sc.nextInt();
            System.out.print("Enter second number: ");
            int b = sc.nextInt();

            int result = stub.subtract(a, b);
            System.out.println("Result: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
