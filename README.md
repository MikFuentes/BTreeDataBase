# B-Tree Database
This is a B-Tree data structure capable of sorting, searching, inserting, and deleting text files.

## Building
Compile `btdb.java` from the command line using:
```
javac btdb.java
```
Then run `btdb.class`, using `BTree` and `Values` files as input. 
Look at the following as an example:
```
java btdb btdb.bt btdb.values
```

## Commands
Note: `key` and `word` are of types `long` and `String` respectively
| Command | Description |
|---|---|
|`insert <key> <word>`|Creates a node that holds the given word and inserts it into the database|
|`select <key>`|Reads the word of the specified node|
|`update <key> <word>`|Updates the word stored at the specified node|
|`exit`|Exit the program|