/* 
import StringReverseModule.StringReversePOA;

public class StringReverseImpl extends StringReversePOA {
    @Override
    public String reverseString(String input) {
        System.out.println("Client sent: " + input);
        return new StringBuilder(input).reverse().toString();
    }
}

*/

/*
STRING REVERSE CORBA IMPLEMENTATION:
1. This class contains the remote method logic.
2. Client sends one string to reverseString().
3. Server prints the received string.
4. Method returns the reversed string to the client.

SAMPLE:
Input : hello
Output: olleh
*/
