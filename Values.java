import java.io.*;

/**
 * <h1>Values!</h1>
 * This Value class contains all the value strings of the database.
 *
 * @author Mikael Fuentes, Magnus Untal, and Nigel Yu 
 * @1.0
 * @since 2017-12-06
 */
public class Values{
	private long recordCount;
	private final int BYTE_LENGTH = 258;
	private final long RECORD_COUNT_OFFSET = 0;
	private final long INITIAL_OFFSET = 8;
	private RandomAccessFile valuesFile;
	
	/**
     * This is the constructor for the values
     *
     * @param name the name of the file
     */
	public Values(String name){
		try{
			File file = new File(name);
			if(file.exists()){
				valuesFile = new RandomAccessFile(name, "rwd");
				valuesFile.seek(RECORD_COUNT_OFFSET);
				recordCount = valuesFile.readLong();
			}
			else{
				valuesFile = new RandomAccessFile(name, "rwd");
				valuesFile.seek(RECORD_COUNT_OFFSET);
				recordCount = 0;
				valuesFile.writeLong(recordCount);
			}
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}

	/**
     * This method is used to write to the file.
     *
     * @param b the bytes to be written 
     */
	public void writeToFile(byte[] b) throws IOException{
		valuesFile.seek(INITIAL_OFFSET+recordCount*BYTE_LENGTH);
		register(b);
		recordCount++;
		valuesFile.seek(0);
		valuesFile.writeLong(recordCount);
	}

	/**
     * This method is used to read the file. 
     *
     * @param record the record number to be
     * @return toReturn the string stored in the values file at the record number
     */
	public String readValues(long record)throws IOException{
		valuesFile.seek(INITIAL_OFFSET+record*BYTE_LENGTH);
		short stringLength = valuesFile.readShort();
		byte[] byteArray = new byte[stringLength];
		for(int i = 0; i<stringLength; i++){
			byteArray[i] = valuesFile.readByte();
		}
		String toReturn = new String(byteArray,"UTF8");
		return toReturn;
	}
	
	/**
     * This method is used to count the number of records.
     *
     * @return recordCount the number of records as a long
     */
	public long getRecord(){
		return recordCount;
	}

	/**
     * This method is used to update the file.
     *
     * @param b the bytes to be written 
     * @param l the record number
     */
	public void updateFile(byte[] b, long l) throws IOException{
		valuesFile.seek(INITIAL_OFFSET+l*BYTE_LENGTH);
		register(b);
	}

	/**
     * This method is used to register the string
     *
     * @param b the bytes to be written 
     */
	private void register(byte[] b) throws IOException{
		valuesFile.writeShort(b.length);
		valuesFile.write(b);
	}
}