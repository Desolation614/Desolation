package dragonkk.rs2rsps.util.save;

import java.util.*;
import java.util.logging.Logger;

public class SaveTaskManager implements Runnable {
	
	/**
	 * Constructor
	 */
	public SaveTaskManager() {
		setEngineRunning(true);
		setAcceptingTasks(true);
	}

	/**
	 * A queue of save task's
	 */
	private Queue<SaveTask> saveTaskQueue = new LinkedList<SaveTask>();
	
	/**
	 * A Logger for the manager
	 */
	private Logger logger = Logger.getLogger(SaveTaskManager.class.getName());
	
	/**
	 * Is the engine running?
	 */
	private boolean engineRunning = false;
	
	/**
	 * Is the worker accepting tasks?
	 */
	private boolean acceptingTasks = false;
	
	/**
	 * Ran when invoked
	 */
	@Override
	public void run() {
		while(isEngineRunning()) {	
			if(!getSaveTaskQueue().isEmpty()) {
				SaveTask task = getSaveTaskQueue().poll();
				if(task != null)
					try {
						task.executeTask();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		if(getSaveTaskQueue().isEmpty()) {
			getLogger().info("Queue was empty. Shutting down.");
		} else {
			getLogger().info("Queue had remaining tasks. Finishing tasks");
			for(SaveTask task : getSaveTaskQueue()) {
				try {
					task.executeTask();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			getSaveTaskQueue().clear();
			getLogger().info("Tasks finished. Shutting down service.");
		}
	}
	
	/**
	 * Submits a save task.
	 * @param task
	 */
	public void submitSave(SaveTask task) {
		if(!isAcceptingTasks()) {
			getLogger().info("Working not accepting tasks. Save Task rejected.");
			return;
		}
		getSaveTaskQueue().add(task);
	}
	
	/**
	 * Waits for a finish
	 */
	public void waitForFinish() {
		while(!getSaveTaskQueue().isEmpty()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	/**
	 * Shuts down the worker
	 */
	public void shutdown() {
		setAcceptingTasks(false);
		waitForFinish();
		setEngineRunning(false);
	}

	/**
	 * @param engineRunning the engineRunning to set
	 */
	public void setEngineRunning(boolean engineRunning) {
		this.engineRunning = engineRunning;
	}

	/**
	 * @return the engineRunning
	 */
	public boolean isEngineRunning() {
		return engineRunning;
	}

	/**
	 * @param saveTaskQueue the saveTaskQueue to set
	 */
	public void setSaveTaskQueue(Queue<SaveTask> saveTaskQueue) {
		this.saveTaskQueue = saveTaskQueue;
	}

	/**
	 * @return the saveTaskQueue
	 */
	public Queue<SaveTask> getSaveTaskQueue() {
		return saveTaskQueue;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param acceptingTasks the acceptingTasks to set
	 */
	public void setAcceptingTasks(boolean acceptingTasks) {
		this.acceptingTasks = acceptingTasks;
	}

	/**
	 * @return the acceptingTasks
	 */
	public boolean isAcceptingTasks() {
		return acceptingTasks;
	}

}