/* 
import StringReverseModule.StringReverse;
import StringReverseModule.StringReverseHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class StringReverseServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            StringReverseImpl reverseImpl = new StringReverseImpl();
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(reverseImpl);
            StringReverse href = StringReverseHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = ncRef.to_name("StringReverseService");
            ncRef.rebind(path, href);

            System.out.println("String Reverse CORBA Server is ready.");
            orb.run();
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

*/

/*
STRING REVERSE CORBA SERVER - CHECK/RUN:
1. Use Java 8 because idlj, orbd, and CORBA APIs are available there.
2. Remove // comments from StringReverseClient.java, StringReverseServer.java,
   and StringReverseImpl.java before running.
3. Generate stub/skeleton files:
   idlj -fall StringReverse.idl
4. Compile:
   javac *.java StringReverseModule/*.java
5. Start naming service:
   orbd -ORBInitialPort 1050
6. Start this server:
   java StringReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost

EXPECTED SERVER OUTPUT:
String Reverse CORBA Server is ready.
Client sent: hello
*/
