package dragonkk.rs2rsps.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.util.Misc;

public class BanSystem {

	private static RandomAccessFile usersStatus;
	private static final Map <String, Integer> status = new HashMap<String, Integer>();
	private static boolean updateneeded = false;
	public static void main(String[] args) {
		new BanSystem();
	}
	public BanSystem() {
		readStatus();
		setStatus("dragonkkk", 0);
		setStatus("sunny_earth", 1);
		Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	if(updateneeded)
                	remakeList();
                }
        });
	}
	
	public static void setStatus(String Username, int Status) {
		if(status.containsKey(Username) || Status == 0)
			status.remove(Username);
		updateneeded = true;
		if(Status == 0)
			return;
		status.put(Username, Status);
	}
	public static void remakeList() {
		new File("BannedUsers.dat").delete();
		if(status.isEmpty())
			return;
		try {
			usersStatus = new RandomAccessFile("BannedUsers.dat", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			Object[] UserUsernames = status.keySet().toArray();
			Object[] UserStatus = status.values().toArray();
			for(int index = 0; index < UserUsernames.length; index++) {
				usersStatus.writeLong(Misc.stringToLong((String) UserUsernames[index]));
				usersStatus.writeByte((Integer) UserStatus[index]);
			}
			usersStatus.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void readStatus() {
		try {
			usersStatus = new RandomAccessFile("BannedUsers.dat", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			if(usersStatus.length() == 0)
				return;
			while(usersStatus.getFilePointer() < usersStatus.length()) {
				status.put(Misc.longToString(usersStatus.readLong()), usersStatus.readUnsignedByte());
			}
			usersStatus.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
