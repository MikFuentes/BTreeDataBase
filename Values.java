import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
    * Constructor Values initializes the file to be used for the Values file
    *
    * @param name 	the name of the file
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
     * Method writeToFile is used to write to the file.
     *
     * @param b 	byte[] where the bytes are written 
     */
	public void writeToFile(byte[] b) throws IOException{
		valuesFile.seek(INITIAL_OFFSET+recordCount*BYTE_LENGTH);
		register(b);
		recordCount++;
		valuesFile.seek(0);
		valuesFile.writeLong(recordCount);
	}

	/**
     * Method readValues is used to read the file.
     *
     * @param record 		long value indicating the record number to be
     * @return toReturn 	the string stored in the values file at the record number
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
    * Method getRecord is used to count the number of records.
    *
    * @return recordCount 	long value indicating the the number of records
    */
	public long getRecord(){
		return recordCount;
	}

	/**
    * Method updateFile is used to update the file.
    *
    * @param b 		byte[] where the bytes are written 
    * @param l 		long value indicating the record number
    */
	public void updateFile(byte[] b, long l) throws IOException{
		valuesFile.seek(INITIAL_OFFSET+l*BYTE_LENGTH);
		register(b);
	}

	/**
    * Method register is used to register the string.
    *
    * @param b 		byte[] where the bytes are written 
    */
	private void register(byte[] b) throws IOException{
		valuesFile.writeShort(b.length);
		valuesFile.write(b);
	}
}