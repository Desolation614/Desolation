package dragonkk.rs2rsps;

import java.util.ArrayList;

public class HostList {

	public static final int IPS_ALLOWED = 5; // how many ips are allowed
	public static ArrayList <String>connectedIps = new ArrayList<String> ();
	
	public static void addIp(String IP) {
		connectedIps.add(IP);
	}

	public static void removeIp(String IP) {
		connectedIps.remove(IP);
	}
	
	public static boolean containsIp(String IP) {
		int connections = 0;	
		for(String con : connectedIps) {
			if(con.equalsIgnoreCase(IP)) {
				connections++;
			}
		}	
		if(connections >= IPS_ALLOWED) {
			return true;
		}
		return false;		
	}
}