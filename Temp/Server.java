
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;



public class Server{
    public static void main(String[] args) {
        try {
            VowelsImpl obj=new VowelsImpl();
            try {
                LocateRegistry.createRegistry(1099);
            } catch (Exception e) {
                System.err.println("Register alredy runnning"+e);
            }
            Naming.rebind("VowelsCount", obj);
            System.out.println("Server is ready..");
        } catch (Exception e) {
            System.err.println("Server erorr"+e);
        }
    }
}