package dragonkk.rs2rsps.event;

import java.util.*;
import java.util.concurrent.ExecutionException;

import dragonkk.rs2rsps.Server;

public class ParallelTask implements ThreadTask {
	
	/**
	 * The child tasks.
	 */
	private Collection<ThreadTask> tasks;
	
	/**
	 * Creates the parallel task.
	 * @param tasks The child tasks.
	 */
	public ParallelTask(ThreadTask... tasks) {
		List<ThreadTask> taskList = new ArrayList<ThreadTask>();
		for(ThreadTask task : tasks) {
			taskList.add(task);
		}
		this.tasks = Collections.unmodifiableCollection(taskList);
	}
	
	@Override
	public void execute() {
		for(final ThreadTask task : tasks) {
			Server.getEngine().submitTask(new Runnable() {
				@Override
				public void run() {
					task.execute();
				}
			});
		}
		try {
			Server.getEngine().taskService.waitForPendingTasks();
		} catch(ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
