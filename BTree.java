import java.io.*;
import java.util.*;

public class BTree{
	private final long RECORD_COUNT_OFFSET = 0;
	private final int ORDER = 7;
	private long nodeCount;
	private RandomAccessFile bTreeFile;
	private ArrayList<Node> nodeList;

	public BTree(String name){
		try{
			File file = new File(name);
			bTreeFile = new RandomAccessFile(name,"rwd");
			bTreeFile.seek(RECORD_COUNT_OFFSET);
			if(file.exists()){
				nodeCount = bTreeFile.readLong();
			}
			else{
				nodeCount = 0;
				bTreeFile.writeLong(nodeCount);
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}
	public void split(){ //method for spliiting B-Tree **to be continued

	}
	public void writeToFile(byte[] b){ //**incomplete???
		
	}


	class Node{ //node class
		private long[] pointer;
		public Node(){ //each node has a long[]
			pointer = new long[3*ORDER-1]; //long[] of size 20
			for(int i = 0; i<pointer.length; i++){
				pointer[i] = -1; //each index gains a value of -1
			}
		}

		public boolean isRoot(){ //checks if the selected node is the root
			if(node.pointer[0]==-1){ //**not sure about this
				return true;
			}
			else{
				return false;
			} 
		}
	}
}
