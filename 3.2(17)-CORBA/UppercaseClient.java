// import UppercaseModule.Uppercase;
// import UppercaseModule.UppercaseHelper;
// import org.omg.CORBA.ORB;
// import org.omg.CosNaming.NamingContextExt;
// import org.omg.CosNaming.NamingContextExtHelper;
// import java.util.Scanner;

// public class UppercaseClient {
//     public static void main(String[] args) {
//         try (Scanner sc = new Scanner(System.in)) {
//             ORB orb = ORB.init(args, null);

//             org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//             NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

//             Uppercase uppercaseObj = UppercaseHelper.narrow(ncRef.resolve_str("UppercaseService"));

//             System.out.print("Enter string: ");
//             String input = sc.nextLine();

//             String result = uppercaseObj.changeToUppercase(input);
//             System.out.println("Uppercase string: " + result);
//         } catch (Exception e) {
//             System.out.println("Client exception: " + e.toString());
//             e.printStackTrace();
//         }
//     }
// }

/*
UPPERCASE CORBA - CHECK/RUN/INPUT:
1. Use Java 8; check: java -version, javac -version, idlj, orbd.
2. Generate files: idlj -fall Uppercase.idl
3. Compile: javac *.java UppercaseModule/*.java
4. Start: orbd -ORBInitialPort 1050
5. Run server, then client using commands in RunCommands.txt.
6. Input one string; output shows uppercase string.
*/
