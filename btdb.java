import java.util.*;
import java.io.*;

/**
* <h1>btdb!</h1>
* Main class for the project
* Handles inputs, and errors for the BTree and the Value files
* 
* @author Mikael Fuentes, Magnus Untal, and Nigel Yu 
* @1.0
* @since 2017-12-06
*/
public class btdb{
	/**
	* Main method for btdb class
	* Passes the required files to the Driver class.
	* @param args 	accepts BTree and Values files
	*/
	public static void main(String[] args) throws IOException{
		if(args.length!=2){
			System.out.println("ERROR: Invalid Argument\nRequires Two File Arguments");
		}
		else{
			String btData = args[0];
			String valuesData = args[1];
			Driver d = new Driver(btData,valuesData);
			Scanner in = new Scanner(System.in);
			String input = in.next().toLowerCase();
			System.out.println("Input a command:");
			while(!input.equals("exit")) {
				if(input.equals("select")) {
					System.out.println(d.select(in.nextLong()));
				}
				else if(input.equals("insert")) {
					System.out.println(d.insert(in.nextLong(),in.nextLine()));
				}
				else if(input.equals("update")) {
					System.out.println(d.update(in.nextLong(),in.nextLine()));
				}
				else {
					System.out.println("ERROR: invalid command.");
				}
				input = in.next().toLowerCase();
			}
			
			in.close();
		}
	}
	
}