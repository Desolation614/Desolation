package dragonkk.rs2rsps.cache;

import java.io.IOException;
import java.io.RandomAccessFile;

import dragonkk.rs2rsps.io.OutStream;

public class DataSystemWriter {

	private RandomAccessFile data;
	private int lastFilePosition;
	
	public int addData(OutStream file) throws IOException {
		int position = lastFilePosition;
		lastFilePosition += file.offset();
		getData().seek(position);
		getData().write(file.buffer(), 0, file.offset());
		return position;
	}
	
	public DataSystemWriter(RandomAccessFile data) {
		this.setData(data);
	}

	public void setData(RandomAccessFile data) {
		this.data = data;
	}

	public RandomAccessFile getData() {
		return data;
	}
	
	
}
