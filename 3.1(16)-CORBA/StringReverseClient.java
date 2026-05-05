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
2. Remove the starting /* and ending */ block comment markers from
   StringReverseClient.java, StringReverseServer.java, and StringReverseImpl.java.
3. Generate files:
   idlj -fall StringReverse.idl

LINUX COMMANDS:
javac *.java StringReverseModule/*.java
orbd -ORBInitialPort 1050
java StringReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost
java StringReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost

WINDOWS COMMANDS:
javac *.java StringReverseModule\*.java
orbd -ORBInitialPort 1050
java StringReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost
java StringReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost

INPUT:
Enter string: hello

OUTPUT:
Reversed string: olleh
*/
