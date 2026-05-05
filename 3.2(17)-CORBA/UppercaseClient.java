/* 
import UppercaseModule.Uppercase;
import UppercaseModule.UppercaseHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import java.util.Scanner;

public class UppercaseClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Uppercase uppercaseObj = UppercaseHelper.narrow(ncRef.resolve_str("UppercaseService"));

            System.out.print("Enter string: ");
            String input = sc.nextLine();

            String result = uppercaseObj.changeToUppercase(input);
            System.out.println("Uppercase string: " + result);
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

*/


/*
UPPERCASE CORBA - CHECK/RUN/INPUT:
1. Use Java 8; check: java -version, javac -version, idlj, orbd.
2. Remove the starting /* and ending */ block comment markers from
   UppercaseClient.java, UppercaseServer.java, and UppercaseImpl.java.
3. Generate files:
   idlj -fall Uppercase.idl

LINUX COMMANDS:
javac *.java UppercaseModule/*.java
orbd -ORBInitialPort 1050
java UppercaseServer -ORBInitialPort 1050 -ORBInitialHost localhost
java UppercaseClient -ORBInitialPort 1050 -ORBInitialHost localhost

WINDOWS COMMANDS:
javac *.java UppercaseModule\*.java
orbd -ORBInitialPort 1050
java UppercaseServer -ORBInitialPort 1050 -ORBInitialHost localhost
java UppercaseClient -ORBInitialPort 1050 -ORBInitialHost localhost

INPUT:
Enter string: hello

OUTPUT:
Uppercase string: HELLO
*/
