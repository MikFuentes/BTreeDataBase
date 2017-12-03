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
	/**
	*Method writeNode outputs a node's values to a file
	*a specified node is searched for in the B-tree, then its values are read and outputted
	*values include:
	*	1.the parent of the node (the node lies in the parent's children array)
	*	2.the IDs of the node's children
	*	3.the node's keys
	*	4.offset to indicate distance from beginning of the array to outputted keys and children
	*	
	*@param node		specified node to output values from
	*@param location	long that indicates the node's location in the B-Tree
	*/
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
	/**
	*Method readNode returns values stored in a file and writes them to a new node
	*the new node is placed in a specified location in the B-tree 
	*values returned include: 
	*	1.a new node
	*	2.the parent of the node (the node lies in the parent's children array)
	*	3.the IDs of the node's children
	*	4.the node's keys
	*	5.offset to indicate distance from beginning of the array to outputted keys and children
	*
	*	
	*@param location	long that indicates the node's location in the B-Tree
	*@return toReturn 	returns node's values read from file
	*/
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
	
	/**
	*Class Node is a constructor for the nodes that will placed into the B-tree
	*Note that unlike BST nodes, B-tree nodes have multiple branches 
	*This means that a set or array of children must be specified, instead of just a right and left child
	*B-tree nodes also store multiple key values, instead of a single value
	*To store keys, a set or array can be used
	*
	*@field keys		array of (primitive data type) longs that refer to key values of a node
	*@field childID		array of (primitive data type) longs that refer to the children/branches of a node
	*@field recordOffset
	*@field parentPointer	long that points to the parent node of a node 
	*/

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
