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
