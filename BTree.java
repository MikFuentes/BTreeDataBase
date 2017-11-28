import java.io.*;

public class BTree{
	private final long RECORD_COUNT_OFFSET = 0;
	private final int ORDER = 7;
	private long nodeCount;
	private RandomAccessFile bTreeFile;
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
	public void writeToFile(byte[] b){
		
	}

	class Node{
		private long[] pointer;
		public Node(){
			pointer = new long[3*ORDER-1];
			for(int i = 0; i<pointer.length; i++){
				pointer[i] = -1;
			}
		}
		public boolean isRoot(Node node){
			if(node.pointer[0]==)
		}
	}
}