import java.io.*;

public class Values{
	private long recordCount;
	private final int BYTE_LENGTH = 256;
	private final long RECORD_COUNT_OFFSET = 0;
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
				recordCount = 0;
				valuesFile = new RandomAccessFile(name, "rwd");
				valuesFile.seek(RECORD_COUNT_OFFSET);
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
		valuesFile.seek(8+recordCount*BYTE_LENGTH);
		register(recordCount,b);
		recordCount++;
	}

	public void updateFile(byte[] b, long recordNum) throws IOException{
		valuesFile.seek(8+recordNum*BYTE_LENGTH);
		register(recordNum, b);
	}

	private void register(long record, byte[] b) throws IOException{
		valuesFile.writeLong(record);
		valuesFile.writeShort(b.length);
		valuesFile.write(b);
	}
}