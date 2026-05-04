
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Vowels extends Remote{
    public int Count(String str) throws RemoteException;
}