// import StringReverseModule.StringReverse;
// import StringReverseModule.StringReverseHelper;
// import org.omg.CORBA.ORB;
// import org.omg.CosNaming.NamingContextExt;
// import org.omg.CosNaming.NamingContextExtHelper;
// import java.util.Scanner;

// public class StringReverseClient {
//     public static void main(String[] args) {
//         try (Scanner sc = new Scanner(System.in)) {
//             ORB orb = ORB.init(args, null);

//             org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//             NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

//             StringReverse reverseObj = StringReverseHelper.narrow(ncRef.resolve_str("StringReverseService"));

//             System.out.print("Enter string: ");
//             String input = sc.nextLine();

//             String result = reverseObj.reverseString(input);
//             System.out.println("Reversed string: " + result);
//         } catch (Exception e) {
//             System.out.println("Client exception: " + e.toString());
//             e.printStackTrace();
//         }
//     }
// }

/*
STRING REVERSE CORBA - CHECK/RUN/INPUT:
1. Use Java 8; check: java -version, javac -version, idlj, orbd.
2. Generate files: idlj -fall StringReverse.idl
3. Compile: javac *.java StringReverseModule/*.java
4. Start: orbd -ORBInitialPort 1050
5. Run server, then client using commands in RunCommands.txt.
6. Input one string; output shows reversed string.
*/
