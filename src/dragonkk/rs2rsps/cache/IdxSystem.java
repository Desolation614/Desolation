package dragonkk.rs2rsps.cache;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.io.InStream;

public class IdxSystem {

	private RandomAccessFile idx;
	private int type;
	private int lastEdit;
	private Map<Object, CacheFile> files;
	
	public IdxSystem(RandomAccessFile idx) {
		this.setIdx(idx);
		loadIdx();
	}
	
	public void loadIdx() {
		try {
			this.setType(this.getIdx().readUnsignedByte());
			this.setLastEdit(this.getIdx().readInt());
			this.setFiles(new HashMap<Object, CacheFile>());
			if(this.getType() == 1) {
				while(this.getIdx().getFilePointer() < this.getIdx().length())
				this.getFiles().put(this.getIdx().readLong(), new CacheFile(this.getIdx().readInt(), this.getIdx().readInt()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InStream getData(Object key) {
		if(this.getType() == 0) {
			if(!this.getFiles().containsKey(key)) {
				try {
					this.getIdx().seek((8*(Integer)key)+5);
					CacheFile file = new CacheFile(this.getIdx().readInt(), this.getIdx().readInt());
					this.getFiles().put(key, file);
					return file.getData();
				} catch (IOException e) {
					return null;
				}
			}
			return this.getFiles().get(key).getData();
		} else if(this.getType() == 1) {
			if(!this.getFiles().containsKey(key))
				return null;
			return this.getFiles().get(key).getData();
		}
		return null;
	}
	public int amtOfFiles() {
		try {
			if(this.getType() == 0)
				return (int) ((idx.length()-5)/8);
			else if (this.getType() == 1)
				return this.getFiles().size();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void setIdx(RandomAccessFile idx) {
		this.idx = idx;
	}

	public RandomAccessFile getIdx() {
		return idx;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setFiles(Map<Object, CacheFile> files) {
		this.files = files;
	}

	public Map<Object, CacheFile> getFiles() {
		return files;
	}

	public void setLastEdit(int lastEdit) {
		this.lastEdit = lastEdit;
	}

	public int getLastEdit() {
		return lastEdit;
	}
	
}
