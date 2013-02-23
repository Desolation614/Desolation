package dragonkk.rs2rsps.util;


import javax.swing.JOptionPane;

import dragonkk.rs2rsps.model.player.Player;

/**
 * NameChanger.java
 * @Author Lay
 */
public class NameChanger {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String from;
		String to;
		Player p = null;
		from = JOptionPane.showInputDialog("Name from?").replaceAll(" ", "_");
		to = JOptionPane.showInputDialog("Name to?");
		try {
			p = Serializer.LoadAccount(from);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setUsername(to);
		Serializer.SaveAccount(p);
		System.out.println("Name change from: "+from+" -> "+to+" completed!");
	}
	

}
