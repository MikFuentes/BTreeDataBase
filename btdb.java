import java.util.*;
import java.io.*;

public class btdb{
	public static void main(String[] args) throws IOException{
		if(args.length!=2){
			System.out.println("ERROR: Invalid Argument\nRequires Two File Arguments");
		}
		else{
			String btData = args[0];
			String valuesData = args[1];
			Values valueFile = new Values(valuesData);
			solve(valueFile);
		}
	}
	public static void solve(Values valueFile){
		Scanner in = new Scanner(System.in);
		String input = in.next();
		try{
			while(!input.equals("exit")){
				if(input.equals("insert")){
					int key = in.nextInt();
					String toStore = in.next();
					valueFile.writeToFile(toStore.getBytes("UTF8"));
				}
				input = in.next();
			}
		}
		catch(IOException ie){
			ie.printStackTrace(System.out);
		}
	}
}