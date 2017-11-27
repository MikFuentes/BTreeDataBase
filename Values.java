import java.io.*;

public class Values{
	private long recordCount;
	private final int BYTE_LENGTH = 258;
	private final long RECORD_COUNT_OFFSET = 0;
	private final long INITIAL_OFFSET = 8;
	private RandomAccessFile valuesFile;
	
	public Values(String name){
		try{
			File file = new File(name);
			if(file.exists()){
				valuesFile = new RandomAccessFile(name, "rwd");
				valuesFile.seek(RECORD_COUNT_OFFSET);
				recordCount = valuesFile.readLong() + 1;
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
	
	public void writeToFile(byte[] b) throws IOException{
		valuesFile.seek(0);
		valuesFile.writeLong(recordCount);
		valuesFile.seek(INITIAL_OFFSET+recordCount*BYTE_LENGTH);
		register(b, recordCount);
		recordCount++;
	}

	public void updateFile(byte[] b, long l) throws IOException{
		valuesFile.seek(INITIAL_OFFSET+l*BYTE_LENGTH);
		register(b, l);
	}

	private void register(byte[] b, long l) throws IOException{
		valuesFile.writeLong(l);
		valuesFile.writeShort(b.length);
		valuesFile.write(b);
	}
}