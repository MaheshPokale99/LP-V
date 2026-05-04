/*
import UppercaseModule.Uppercase;
import UppercaseModule.UppercaseHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class UppercaseServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            UppercaseImpl uppercaseImpl = new UppercaseImpl();
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(uppercaseImpl);
            Uppercase href = UppercaseHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = ncRef.to_name("UppercaseService");
            ncRef.rebind(path, href);

            System.out.println("Uppercase CORBA Server is ready.");
            orb.run();
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
*/

/*
UPPERCASE CORBA SERVER - CHECK/RUN:
1. Use Java 8 because idlj, orbd, and CORBA APIs are available there.
2. Remove // comments from UppercaseClient.java, UppercaseServer.java,
   and UppercaseImpl.java before running.
3. Generate stub/skeleton files:
   idlj -fall Uppercase.idl
4. Compile:
   javac *.java UppercaseModule/*.java
5. Start naming service:
   orbd -ORBInitialPort 1050
6. Start this server:
   java UppercaseServer -ORBInitialPort 1050 -ORBInitialHost localhost

EXPECTED SERVER OUTPUT:
Uppercase CORBA Server is ready.
Client sent: hello
*/
