/* 
import StringReverseModule.StringReverse;
import StringReverseModule.StringReverseHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import java.util.Scanner;

public class StringReverseClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            StringReverse reverseObj = StringReverseHelper.narrow(ncRef.resolve_str("StringReverseService"));

            System.out.print("Enter string: ");
            String input = sc.nextLine();

            String result = reverseObj.reverseString(input);
            System.out.println("Reversed string: " + result);
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

*/

/*
STRING REVERSE CORBA - CHECK/RUN/INPUT:
1. Use Java 8; check: java -version, javac -version, idlj, orbd.
2. Remove // comments from all CORBA Java files before running.
3. Generate files: idlj -fall StringReverse.idl
4. Compile: javac *.java StringReverseModule/*.java
5. Terminal 1: orbd -ORBInitialPort 1050
6. Terminal 2: java StringReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost
7. Terminal 3: java StringReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost

INPUT:
Enter string: hello

OUTPUT:
Reversed string: olleh
*/
