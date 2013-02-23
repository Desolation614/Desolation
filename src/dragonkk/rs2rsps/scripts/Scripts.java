package dragonkk.rs2rsps.scripts;

import java.util.HashMap;
import java.util.Map;

public class Scripts {

	private static final Map <String, Class<?>> dialogueScripts = new HashMap<String, Class<?>>();
	private static final Map <Short, itemScript> itemScripts = new HashMap<Short, itemScript>();
	private static final Map <Short, interfaceScript> interfaceScripts = new HashMap<Short, interfaceScript>();
	private static final Map <Integer, objectScript> objectScripts = new HashMap<Integer, objectScript>();
	public static void resetScripts() {
		dialogueScripts.clear();
	}
	
	public static dialogueScript invokeDialogueScript(String key) {
		if(dialogueScripts.containsKey(key)) {
			try {
				return (dialogueScript) dialogueScripts.get(key).newInstance();
			} catch (InstantiationException e1) {
			} catch (IllegalAccessException e1) {
			}
			return null;
		}
		try {
			Class<?> value = Class.forName("dragonkk.rs2rsps.scripts.dialogues."+key);
			dialogueScripts.put(key, value);
			return (dialogueScript) value.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}
	
	public static itemScript invokeItemScript(short key) {
		if(itemScripts.containsKey(key))
				return (itemScript) itemScripts.get(key);
		try {
			itemScript value = (itemScript) Class.forName("dragonkk.rs2rsps.scripts.items.i"+key).newInstance();
			itemScripts.put(key, value);
			
			return value;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		}
		itemScript value = new itemScript();
		itemScripts.put(key, value);
		return value;
	}
	
	public static interfaceScript invokeInterfaceScript(short key) {
		if(interfaceScripts.containsKey(key))  {
			
				return (interfaceScript) interfaceScripts.get(key);
		}
		try {
			interfaceScript value = (interfaceScript) Class.forName("dragonkk.rs2rsps.scripts.interfaces.i"+key).newInstance();
			interfaceScripts.put(key, value);
			return value;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}
	
	public static objectScript invokeObjectScript(int key) {
		if(objectScripts.containsKey(key))
				return (objectScript) objectScripts.get(key);
		try {
			objectScript value = (objectScript) Class.forName("dragonkk.rs2rsps.scripts.objects.o"+key).newInstance();
			objectScripts.put(key, value);
			return value;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}
	
}
