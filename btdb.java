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
					long key = in.nextLong();
					String toStore = in.nextLine();
					valueFile.writeToFile(toStore.trim().getBytes("UTF8"));
				}
				else if(input.equals("select")){
					long key = in.nextLong();
					System.out.println(valueFile.readValues(key));
				}
				else if(input.equals("update")){
					long key = in.nextLong();
					String toStore = in.nextLine();
					valueFile.updateFile(toStore.trim().getBytes("UTF8"), key);
				}

				input = in.next();
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}
}