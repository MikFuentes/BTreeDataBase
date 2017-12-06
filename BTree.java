import java.io.*;
import java.util.*;

public class BTree{
	private final int ORDER = 7;
	private final int CENTER = (ORDER-1)/2;
	private final long RECORD_COUNT_OFFSET = 0;
	private final long INITIAL_OFFSET = 16;
	private final long NODE_LENGTH = (3*ORDER-1)*8;	

	private long nodeCount, rootFinder;
	private RandomAccessFile bTreeFile;
	private ArrayList<Node> nodeList;
	private ArrayList<Long> childInformation;

	public BTree(String name){

		try{
			File file = new File(name);
			if(file.exists()){
				bTreeFile = new RandomAccessFile(name,"rwd");
				bTreeFile.seek(RECORD_COUNT_OFFSET);
				nodeCount = bTreeFile.readLong();
				rootFinder = bTreeFile.readLong();
				nodeList = new ArrayList<>();
				childInformation = new ArrayList<>();
			}
			else{
				bTreeFile = new RandomAccessFile(name,"rwd");
				bTreeFile.seek(RECORD_COUNT_OFFSET);
				nodeCount = 1;
				rootFinder = 0;
				bTreeFile.writeLong(nodeCount);
				bTreeFile.writeLong(rootFinder);
				Node initial = new Node(0);
				writeNode(initial);
				initial = null;
				nodeList = new ArrayList<>();
				childInformation = new ArrayList<>();
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}
	/**
	*Method insert adds key values and the offset value of a node to the node's values
	*calls multiple other methods, as well as recursively calling itself
	*uses other methods to check for existing keys and write to files
	*if a key value already in the node is inserted, prints output indicating that the key value already exists
	*
	*@param key				long indicating a key value of a node
	*@param nodeLocation	long value indicating a node's location in the B-tree		
	*@param offset			long value indicating a node's location in the values file
	*@throws IOException
	*@see IOException
	*/
	public void insert(long key, long nodeLocation, long offset) throws IOException{// method for inserting keys and their respective offset **complete for now
		
		Node checker = readNode(nodeLocation);

		if(!checker.hasChild()){
			checker.insertKey(key,offset);	
		}

		else{
			long newLocation = checker.findChild(key);
			checker = null;
			insert(key,newLocation,offset);
		}

		if(checker!=null){
			if(checker.keys[ORDER-1]!=-1)
				split(checker);
			else{
				writeNode(checker);
			}
			checker = null;
		}
		
	}

	private void split(Node node) throws IOException{ //method for spliiting B-Tree **to be continued
		if(node.parentPointer == -1){
			Node rightChild = new Node(nodeCount);
			nodeCount++;
			Node root = new Node(nodeCount);
			nodeCount++;
			node.transferInformation(root,rightChild);
			node.parentPointer = root.nodeID;
			rightChild.parentPointer = root.nodeID;
			root.addChild(node.nodeID);
			root.addChild(rightChild.nodeID);
			writeNode(node);
			writeNode(rightChild);
			writeNode(root);
			writeHeader(root);
			node = null;
			rightChild = null;
			root = null;
		}
		else{
			Node parent = readNode(node.parentPointer);
			Node rightChild = new Node(nodeCount);
			nodeCount++;
			node.transferInformation(parent,rightChild);
			if(parent.keys[ORDER-1]!=-1){
				split(parent);
			}
		}
	}

	private void writeHeader(Node root) throws IOException{
		bTreeFile.seek(0);
		bTreeFile.writeLong(nodeCount);
		bTreeFile.writeLong(root.nodeID);
	}
	public long findRoot()throws IOException{
		bTreeFile.seek(8);
		return bTreeFile.readLong();
	}
	
	public long findKey(long key, long location) throws IOException{
		Node checker = readNode(location);
		for(int i = 0; i<ORDER-1; i++){
			if(checker.keys[i]==key)
				return checker.recordsOffset[i];
			else if((checker.keys[i]<key&&checker.keys[i+1]==-1)
				&&checker.childID[i+1]!=-1){
				long newLocation = checker.childID[i+1];
				checker = null;
				return findKey(key, newLocation);
			}
			else if(checker.keys[i]>key&&checker.childID[i]!=-1){
				long newLocation = checker.childID[i];
				checker = null;
				return findKey(key,newLocation);
			}
			else if(i==ORDER-2&&checker.childID[i+1]!=-1){
				long newLocation = checker.childID[i+1];
				checker = null;
				return findKey(key,newLocation);
			}
		}
		return -1;
	}
	/**
	*Method writeNode outputs a node's values to a file
	*a specified node is searched for in the B-tree, then its values are read and outputted
	*values include:
	*	1.the parent of the node (the node lies in the parent's children array)
	*	2.the IDs of the node's children
	*	3.the node's keys
	*	4.the node's position in the values file (offset)
	*
	*@param node		specified node to output values from
	*@param location	long that indicates the node's location in the B-Tree
	*/
	private void writeNode(Node node) throws IOException{// used when writing to file
		bTreeFile.seek(INITIAL_OFFSET+node.nodeID*NODE_LENGTH);
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
	*	5.the node's position in the values file (offset)
	*
	*
	*@param location	long that indicates the node's location in the B-Tree
	*@return toReturn 	returns node read from file
	*/

	private Node readNode(long location) throws IOException{//used when inserting to nodes
		bTreeFile.seek(INITIAL_OFFSET+location*NODE_LENGTH);
		Node toReturn = new Node(location);
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
	*Inner Class Node is used for the nodes that will placed into the B-tree
	*Note that unlike BST nodes, B-tree nodes have multiple branches
	*This means that a set or array of children must be specified, instead of just a right and left child
	*B-tree nodes also store multiple key values, instead of a single value
	*To store keys, a set or array can be used
	*
	*@field keys		array of (primitive data type) longs that refer to key values of a node
	*@field childID		array of (primitive data type) longs that refer to the children/branches of a node
	*@field recordsOffset	node's position in the values file
	*@field parentPointer	long that points to the parent node of a node
	*/
	class Node{ //node class
		private long[] keys, childID, recordsOffset;
		private long parentPointer, nodeID;
		private Node(long nodeLocation){ //each node has 3 long[]
			keys = new long[ORDER];
			childID = new long[ORDER];
			recordsOffset = new long[ORDER];
			parentPointer = -1;
			nodeID = nodeLocation;
			for(int i = 0; i<ORDER; i++){
				keys[i] = -1;
				childID[i] = -1;
				recordsOffset[i] = -1;
			}
		}

		private boolean hasChild(){
			if(childID[0]!=-1)
				return true;
			return false;
		}

		private long findChild(long key){
			long id = -1;
			for(int i = 0; i<ORDER-1; i++){
				if(key>keys[i]&&key<keys[i+1])
					return childID[i];
			}
			return id;
		}

		private void addChild(long nodeLocation){
			for(int i = 0; i<ORDER; i++){
				if(childID[i]==-1){
					childID[i] = nodeLocation;
					break;
				}
			}
		}

		private void insertKey(long key, long offset){
			for(int i = ORDER-2; i>=0; i--){
				if(keys[i] == -1){
					if(i==0){
						keys[i] = key;
						recordsOffset[i] = offset;
						
					}
					continue;
				}
				if(key<keys[i]){
					keys[i+1] = keys[i];
					recordsOffset[i+1] = recordsOffset[i];
					if(i==0){
						keys[i] = key;
						recordsOffset[i] = offset;
					}
				}
				else{
					keys[i+1] = key;
					recordsOffset[i+1] = offset;
					break;
				}
			}
		}
		private void transferInformation(Node root, Node sibling){
			root.insertKey(keys[CENTER],recordsOffset[CENTER]);
			keys[CENTER] = -1;
			recordsOffset[CENTER] = -1;
			for(int i = CENTER+1; i<ORDER; i++){
				sibling.insertKey(keys[i],recordsOffset[i]);
				sibling.addChild(childID[i]);
				keys[i] = -1;
				recordsOffset[i] = -1;
				childID[i] = -1;
			}
		}
	}
}
