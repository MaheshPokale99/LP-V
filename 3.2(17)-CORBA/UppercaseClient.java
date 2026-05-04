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
2. Remove // comments from all CORBA Java files before running.
3. Generate files: idlj -fall Uppercase.idl
4. Compile: javac *.java UppercaseModule/*.java
5. Terminal 1: orbd -ORBInitialPort 1050
6. Terminal 2: java UppercaseServer -ORBInitialPort 1050 -ORBInitialHost localhost
7. Terminal 3: java UppercaseClient -ORBInitialPort 1050 -ORBInitialHost localhost

INPUT:
Enter string: hello

OUTPUT:
Uppercase string: HELLO
*/
