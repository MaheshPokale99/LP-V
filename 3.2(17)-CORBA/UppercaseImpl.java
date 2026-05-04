/* 
import UppercaseModule.UppercasePOA;

public class UppercaseImpl extends UppercasePOA {
    @Override
    public String changeToUppercase(String input) {
        System.out.println("Client sent: " + input);
        return input.toUpperCase();
    }
}

*/



/*
UPPERCASE CORBA IMPLEMENTATION:
1. This class contains the remote method logic.
2. Client sends one string to changeToUppercase().
3. Server prints the received string.
4. Method returns the uppercase string to the client.

SAMPLE:
Input : hello
Output: HELLO
*/
