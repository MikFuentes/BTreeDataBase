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
			driver(valueFile,bTreeFile);
		}
	}
	public static void driver(Values valueFile, BTree bTreeFile){
		Scanner in = new Scanner(System.in);
		String input = in.next();
		try{
			while(!input.equals("exit")){
				if(input.equals("insert")){
					
					long key = in.nextLong();
					String toStore = in.nextLine();
					if(bTreeFile.findKey(key,bTreeFile.findRoot())==-1){
						long recordNum = valueFile.getRecord();
						//System.out.printf("Root is at %d",bTreeFile.findRoot());
						bTreeFile.insert(key,bTreeFile.findRoot(),recordNum);
						valueFile.writeToFile(toStore.trim().getBytes("UTF8"));
						System.out.printf("%d inserted.\n",key);
					}
					else
						System.out.println("ERROR: key already exists.");
					
				}
				else if(input.equals("select")){
					long key = in.nextLong();
					long location = bTreeFile.findKey(key,bTreeFile.findRoot());
					if(location!=-1){
						System.out.printf("%d %s\n",key,valueFile.readValues(location));
					}
					else
						System.out.println("ERROR: key does not exist.");
				}
				else if(input.equals("update")){
					long key = in.nextLong();
					String toStore = in.nextLine();
					long location = bTreeFile.findKey(key,bTreeFile.findRoot());
					if(location!=-1){
						valueFile.updateFile(toStore.trim().getBytes("UTF8"), location);
						System.out.printf("%d updated.\n", key);
					}
					else
						System.out.println("ERROR: key does not exist.");
				}
				else{
					System.out.println("ERROR: invalid command.");
				}

				input = in.next();
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}
}