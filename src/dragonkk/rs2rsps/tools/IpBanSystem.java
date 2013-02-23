package dragonkk.rs2rsps.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.util.Misc;

public class IpBanSystem {

	private static RandomAccessFile ipList;
	
	private static final List<Integer> ipadresses = new ArrayList<Integer>();
	
	public static boolean isUpdateNeeded = false;
	
	public static void main(String[] args) { //for test
		new IpBanSystem();
	}
	public IpBanSystem() {
		getIps();
		banIp("127.0.0.1");
		banIp("127.0.0.2");
		banIp("127.0.0.3");
		/*
		 * This part should be added on your close thread
		 * not here.. why create 2threads for samething
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	if(isUpdateNeeded)
                	remakeList();
                }
        });
	}
	
	public static boolean isBanned(String ipAdress) {
		return ipadresses.contains(Misc.IPAddressToNumber(ipAdress));
	}
	
	public static void banIp(String ipAdress) {
		int ipToInt = Misc.IPAddressToNumber(ipAdress);
		if(ipadresses.contains(ipToInt))
			return;
		ipadresses.add(ipToInt);
		try {
			ipList = new RandomAccessFile("BannedIps.dat", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			ipList.seek(ipList.length());
			ipList.writeInt(ipToInt);
			ipList.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void unBanIp(String ipAdress) {
		int ipToInt = Misc.IPAddressToNumber(ipAdress);
		if(!ipadresses.contains(ipToInt))
			return;
		ipadresses.remove((Object)ipToInt);
		isUpdateNeeded = true;
	}
	
	private static void remakeList() {
		new File("BannedIps.dat").delete();
		if(ipadresses.isEmpty())
			return;
		try {
			ipList = new RandomAccessFile("BannedIps.dat", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			for(Object ip : ipadresses.toArray())
				ipList.writeInt((Integer)ip);
			ipList.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		isUpdateNeeded = false;
	}
	
	private static void getIps() {
		try {
			ipList = new RandomAccessFile("BannedIps.dat", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			if(ipList.length() == 0)
				return;
			while(ipList.getFilePointer() < ipList.length()) {
				ipadresses.add(ipList.readInt());
			}
			ipList.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
