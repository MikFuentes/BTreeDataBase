import java.io.*;
import java.util.*;

public class BTree{
	private final long RECORD_COUNT_OFFSET = 0;
	private final long INITIAL_OFFSET = 16;
	private final int ORDER = 7;
	private final long NODE_LENGTH = (3*ORDER-1)*8;
	private long nodeCount, rootFinder;
	private RandomAccessFile bTreeFile;
	private ArrayList<Node> nodeList;

	public BTree(String name){

		try{
			File file = new File(name);
			if(file.exists()){
				bTreeFile = new RandomAccessFile(name,"rwd");
				bTreeFile.seek(RECORD_COUNT_OFFSET);
				nodeCount = bTreeFile.readLong();
				rootFinder = bTreeFile.readLong();

			}
			else{
				bTreeFile = new RandomAccessFile(name,"rwd");
				bTreeFile.seek(RECORD_COUNT_OFFSET);
				nodeCount = 1;
				rootFinder = 0;
				bTreeFile.writeLong(nodeCount);
				bTreeFile.writeLong(rootFinder);
				Node initial = new Node();
				writeNode(initial, 0);
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}
	public void insert(long key, long nodeLocation, long offset) throws IOException{// method for inserting keys and their respective offset **not complete
		Node checker = new Node();
		checker = readNode(nodeLocation);	
		for(int i = ORDER-1; i>=0; i++){
			if(key<checker.keys[i]){
				checker.keys[i+1]=checker.keys[i];
			}
		}
	}
	public void split(){ //method for spliiting B-Tree **to be continued

	}

	public void writeNode(Node node, long location) throws IOException{// used when writing to file
		bTreeFile.seek(INITIAL_OFFSET+location*NODE_LENGTH);
		bTreeFile.writeLong(node.parentPointer);
		for(int i = 0; i<ORDER; i++){
			bTreeFile.writeLong(node.childID[i]);
			if(i!=ORDER-1){
				bTreeFile.writeLong(node.keys[i]);
				bTreeFile.writeLong(node.recordsOffset[i]);
			}
		}
	}

	public Node readNode(long location) throws IOException{//used when inserting to nodes
		bTreeFile.seek(INITIAL_OFFSET+location*NODE_LENGTH);
		Node toReturn = new Node();
		toReturn.parentPointer = bTreeFile.readLong();
		for(int i = 0; i<ORDER; i++){
			toReturn.childID[i] = bTreeFile.readLong();
			if(i!=ORDER-1){
				toReturn.keys[i] = bTreeFile.readLong();
				toReturn.recordsOffset[i] = bTreeFile.readLong();
			}
		}
		return toReturn;
	}

	class Node{ //node class
		private long[] keys, childID, recordsOffset;
		private long parentPointer;
		public Node(){ //each node has a long[]
			//pointer = new long[3*ORDER+1]; //handles overflow
			keys = new long[ORDER];
			childID = new long[ORDER];
			recordsOffset = new long[ORDER];
			parentPointer = -1;
			
			for(int i = 0; i<ORDER; i++){
				keys[i] = -1;
				childID[i] = -1;
				recordsOffset[i] = -1;
			}
		}
	}
}
