package dragonkk.rs2rsps.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.event.impl.game.PlayerSaveTask;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.save.SaveTask;

public class Serializer {

	/**
	 * Serialize the object o (and any Serializable objects it refers to) and
	 * store its serialized state in File f.
	 */
	private static void store(Serializable o, File f) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(o); // This method serializes an object graph
		out.close();
	}

	/**
	 * Deserialize the contents of File f and return the resulting object
	 */
	public static Object load(File f) {
		ObjectInputStream in = null;
		Object object = null;
		try {
			in = new ObjectInputStream(new FileInputStream(f));
			object = in.readObject();
		} catch (Exception e) {
			return null;
		}
		if (object == null) {
			return null;
		}
		try {
			in.close();
		} catch (Exception e) {

		}
		in = null;
		return object;
	}

	/**
	 * Loads a players account
	 * 
	 * @param Username
	 *            The username of the account to load
	 * @return The saved serialized java object of the player
	 * @throws IOException
	 *             If a random IO exception occours
	 */
	public static Player LoadAccount(String Username) throws Exception {
		File f = new File(
				"Data/savedgames/"
						+ Username + ".ser");
		try {
			Object player = Serializer.load(f);
			if (player == null) {
				player = null;
				return null;
			} else {
				return (Player) player;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Loads a players account
	 * 
	 * @param Username
	 *            The username of the account to load
	 * @return The saved serialized java object of the player
	 * @throws IOException
	 *             If a random IO exception occours
	 */
	public static Player loadBackup(String Username) throws Exception {
		File f = new File(
				"Data/backup/"
						+ Username + ".ser");
		try {
			Object player = Serializer.load(f);
			if (player == null) {
				player = null;
				return null;
			} else {
				return (Player) player;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void save(Player acc) {
		final File f = new File(
				"Data/savedgames/"
						+ acc.getUsername() + ".ser");
		try {
			store(acc, f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static boolean backupExists(String Username) {
		return new File(
				"Data/backup/"
				+ Username + ".ser").exists();
	}

	/**
	 * Loads a player from a specified file
	 * 
	 * @param f
	 *            The file to load from
	 * @return
	 */
	public static Player LoadAccount(final File f) {
		try {
			Object player = Serializer.load(f);
			if (player == null) {
				player = null;
				return null;
			} else {
				return (Player) player;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Saves an array
	 * 
	 * @param f
	 *            The file to save to
	 * @param array
	 *            The array to save
	 */
	public static void saveBanned(File f, Object array) {
		try {
			store(array, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores a java object
	 * 
	 * @param o
	 *            The object to save
	 * @param f
	 *            The file to save at
	 * @throws IOException
	 */
	private static void store(Object o, File f) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(o);
		out.close();
		out = null;
	}

	/**
	 * Saves a player account via the work engine
	 * 
	 * @author Graham Edgecombe
	 * @param account
	 */
	public static void SaveAccount(final Player account) {
		Server.getEngine().pushTask(new PlayerSaveTask(account));
	}
	
	/**
	 * Saves a player account via the work engine
	 * 
	 * @author Graham Edgecombe
	 * @param account
	 */
	public static void saveBackup(final Player account) {
		final File f = new File(
				"Data/backup/"
						+ account.getUsername() + ".ser");
		Server.getEngine().submitLogic(new Runnable() {

			@Override
			public void run() {
				try {
					store(account, f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		});
		

	}

	/**
	 * Saves a player account via the work engine
	 * 
	 * @author Graham Edgecombe
	 * @param account
	 */
	public static void SaveAccount1(final Player account) {
		
	}

}