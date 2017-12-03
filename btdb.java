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
			BTree bTreeFile = new BTree(btData);
			solve(valueFile,bTreeFile);
		}
	}
	public static void solve(Values valueFile, BTree bTreeFile){
		Scanner in = new Scanner(System.in);
		String input = in.next();
		try{
			while(!input.equals("exit")){
				if(input.equals("insert")){
					
					long key = in.nextLong();
					String toStore = in.nextLine();

					long recordNum = valueFile.getRecord();
					valueFile.writeToFile(toStore.trim().getBytes("UTF8"));
					bTreeFile.insert(key,0,recordNum);

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
				else{
					System.out.println("ERROR: invalid command");
				}

				input = in.next();
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}
}