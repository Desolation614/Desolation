package dragonkk.rs2rsps.cache;

import java.io.IOException;
import java.io.RandomAccessFile;

import dragonkk.rs2rsps.io.InStream;

public class DataSystem {

	private RandomAccessFile data;
	
	public InStream getData(int position, int length) {
		byte[] dataFile = new byte[length];
		try {
			this.getData().seek(position);
			this.getData().readFully(dataFile, 0, length);
		} catch (IOException e) {
			return null;
		}
		InStream instream = new InStream(length);
		instream.addBytes(dataFile, 0, length);
		return instream;
	}
	public DataSystem(RandomAccessFile data) {
		this.setData(data);
	}

	public void setData(RandomAccessFile data) {
		this.data = data;
	}

	public RandomAccessFile getData() {
		return data;
	}
	
}
