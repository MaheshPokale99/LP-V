import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VowelsImpl extends UnicastRemoteObject implements Vowels {
    
    public VowelsImpl() throws RemoteException{
        super();
    }
    @Override
    public int Count(String str) throws RemoteException{
        System.err.print("Clinet req for count the Vowels of  String : " + str);
        int len=str.length();
        int cnt=0;
        for(int i=0; i<len; i++){
            char ch=str.charAt(i);
            ch=Character.toLowerCase(ch);
            if(ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u') cnt++;
        }
        return cnt;
    }
}
