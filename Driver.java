import java.io.IOException;

public class Driver {
	private BTree bt;
	private Values val;
	private String statement = "";

	public Driver(String bTree, String values) {
		bt = new BTree(bTree);
		val = new Values(values);
	}

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
