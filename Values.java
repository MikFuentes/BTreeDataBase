import java.io.*;

public class Values{
	private final int BYTE_LENGTH = 256;
	private final short RECORD_COUNT_OFFSET = 2;
	private RandomAccessFile valuesFile;
	private long recordCount;
	
	public Values(String name) throws IOException{
		File file = new File(name);
		if(file.exists()){
			valuesFile = new RandomAccessFile(name, "rwd");
			valuesFile.seek(RECORD_COUNT_OFFSET);
			recordCount = valuesFile.readLong();
		}
		else{
			recordCount = 0;
			valuesFile = new RandomAccessFile(name, "rwd");
			valuesFile.seek(RECORD_COUNT_OFFSET);
			valuesFile.writeLong(recordCount);
		}
	}
	
	public void writeToFile(byte[] b) throws IOException{
		valuesFile.writeLong(recordCount);
		valuesFile.write(b.length);
		valuesFile.write(b);
		recordCount++;
	}
}