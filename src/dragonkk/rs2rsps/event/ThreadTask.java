package dragonkk.rs2rsps.event;

/**
 * ThreadTask.java
 * @author Administrator
 * Handles the new 
 */
public interface ThreadTask {
	
	/**
	 * Executes the task. The general contract of the execute method is that it
	 * may take any action whatsoever.
	 * @param context The game engine this task is being executed in.
	 */
	public void execute();

}
