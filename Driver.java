import java.io.IOException;

/**
 * <h1>Driver!</h1>
 * This Driver class contains the methods needed to interact with the database.
 *
 * @author Mikael Fuentes, Magnus Untal, and Nigel Yu 
 * @1.0
 * @since 2017-12-06
 */
public class Driver {
	private BTree bt;
	private Values val;
	private String statement = "";

	/**
    * Constructor Driver initializes the files to be used for the Driver class.
    * 
    * @param bTree 	the name of the bTree file
	* @param values the name of the Values file
    */
	public Driver(String bTree, String values) {
		bt = new BTree(bTree);
		val = new Values(values);
	}

	/**
     * Method insert creates a new key node with the given value and inserts it into the BTree.
     *
     * @param key 	the key of the node
	 * @param word  the string value stored in the node
     */
	public String insert(long key, String word) throws IOException {
		if (key > 0) {
			long location = bt.findKey(key, bt.findRoot());
			if (location == -1) {
				long recordNum = val.getRecord();
				bt.insert(key, bt.findRoot(), recordNum);
				val.writeToFile(word.trim().getBytes("UTF8"));
				statement = key + " inserted.";
			} else {
				statement = "ERROR: " + key + " already exists.";
			}
		} else {
			statement = "ERROR: key must be positive.";
		}
		return statement;
	}

	/**
     * Method select will return the value of the key node.
     *
     * @param key 	the key of the node
     */
	public String select(long key) throws IOException {
		if (key > 0) {
			long location = bt.findKey(key, bt.findRoot());
			if (location != -1) {
				statement = key + " => " + (val.readValues(location));
			} else {
				statement = "ERROR: " + key + " does not exist.";
			}
		} else {
			statement = "ERROR: key must be positive.";
		}

		return statement;
	}

	/**
     * Method update is used to update the value of the key node.
     *
     * @param key 	the key of the node
	 * @param word  the string value stored in the node
     */
	public String update(long key, String word) throws IOException {
		if (key > 0) {
			long location = bt.findKey(key, bt.findRoot());
			if (location != -1) {
				val.updateFile(word.trim().getBytes("UTF8"), location);
				statement = key + " updated.";
			} else {
				statement = "ERROR: " + key + " does not exist.";
			}
		} else {
			statement = "ERROR: key must be positive.";
		}
		return statement;
	}
}
