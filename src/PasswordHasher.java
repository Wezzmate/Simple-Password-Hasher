import java.util.Scanner;

/**
 * This is a simple Password hashing program made for education purposes. Not intented for real world security use. 
 * It takes in a password from the first input in the command line arguments and prints out a 32 byte or 256 bit hashed
 * hexidecimal value.
 * 
 * @author Wesley Harden
 * @date 2/16/2026
 */
public class PasswordHasher {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a string to get a 32 byte hash in Hexidecimal format:");
        String userInput = scan.nextLine();
        System.out.println(hashPass(userInput));
        scan.close();
    }

    /**
     * The main password hashing algorithm. Basically goes through the input and assigns each char to a byte in a 32 byte
     * array and does some math and other operations to create a hashed byte array returned as a string.
     * 
     * @param pass The password to hash
     * @return a 32 byte hashed value returned as a hexidecimal string.
     */
    public static String hashPass(String pass){
        int pLen = pass.length();
        byte[] hashed = new byte[32];
        for(int i = 0; i < 32; i++){
            hashed[i] = (byte)(pass.charAt(i % pLen));
        }

        // Many rounds to create avalanche so one change affects whole output.
        for(int j = 0; j < 8; j++){
            for(int p = 0; p < 32; p++){
                    byte temphash = (byte)(hashed[p] + pLen * (p - j));
                    temphash = (byte)(temphash ^ (hashed[(p + 1) % 32]));
                    temphash = (byte)(temphash ^ ((hashed[(p + 31) % 32]) & hashed[p]));
                    temphash = (byte)(temphash ^ (pLen + j + p));
                    hashed[p] = (byte)Integer.rotateLeft((int)temphash, (p + j) % 8);
            }
    }
        return java.util.HexFormat.of().formatHex(hashed);
    }
}
