import java.io.*;

public class Values{
	private long recordCount;
	private final int BYTE_LENGTH = 256;
	private final long RECORD_COUNT_OFFSET = 0;
	private RandomAccessFile valuesFile;
	
	public Values(String name) throws IOException{
		File file = new File(name);
		if(file.exists()){
			valuesFile = new RandomAccessFile(name, "rwd");
			valuesFile.seek(RECORD_COUNT_OFFSET);
			recordCount = valuesFile.readLong() + 1;
		}
		else{
			recordCount = 0;
			valuesFile = new RandomAccessFile(name, "rwd");
			valuesFile.seek(RECORD_COUNT_OFFSET);
			valuesFile.writeLong(recordCount);
		}
	}
	
	public void writeToFile(byte[] b) throws IOException{
		valuesFile.seek(0);
		valuesFile.writeLong(recordCount);
		valuesFile.seek(8+recordCount*BYTE_LENGTH);
		valuesFile.writeLong(recordCount);
		valuesFile.writeShort(b.length);
		valuesFile.write(b);
		recordCount++;
		//System.out.println(recordCount);
		//System.out.println(RECORD_COUNT_OFFSET);
	}
}