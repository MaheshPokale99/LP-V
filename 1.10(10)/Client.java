import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            VowelCount stub = (VowelCount) Naming.lookup("rmi://localhost:1099/VowelCountService");

            System.out.print("Enter a word: ");
            String word = sc.nextLine();

            int result = stub.countVowels(word);
            System.out.println("Number of vowels: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
