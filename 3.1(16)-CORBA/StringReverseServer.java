// import StringReverseModule.StringReverse;
// import StringReverseModule.StringReverseHelper;
// import org.omg.CORBA.ORB;
// import org.omg.CosNaming.NameComponent;
// import org.omg.CosNaming.NamingContextExt;
// import org.omg.CosNaming.NamingContextExtHelper;
// import org.omg.PortableServer.POA;
// import org.omg.PortableServer.POAHelper;

// public class StringReverseServer {
//     public static void main(String[] args) {
//         try {
//             ORB orb = ORB.init(args, null);

//             POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//             rootPOA.the_POAManager().activate();

//             StringReverseImpl reverseImpl = new StringReverseImpl();
//             org.omg.CORBA.Object ref = rootPOA.servant_to_reference(reverseImpl);
//             StringReverse href = StringReverseHelper.narrow(ref);

//             org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//             NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

//             NameComponent[] path = ncRef.to_name("StringReverseService");
//             ncRef.rebind(path, href);

//             System.out.println("String Reverse CORBA Server is ready.");
//             orb.run();
//         } catch (Exception e) {
//             System.out.println("Server exception: " + e.toString());
//             e.printStackTrace();
//         }
//     }
// }
