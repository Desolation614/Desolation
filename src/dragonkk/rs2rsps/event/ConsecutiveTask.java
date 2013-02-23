package dragonkk.rs2rsps.event;

import java.util.*;

public class ConsecutiveTask implements ThreadTask {

	/**
	 * The tasks.
	 */
	private Collection<ThreadTask> tasks;
	
	/**
	 * Creates the consecutive task.
	 * @param tasks The child tasks to execute.
	 */
	public ConsecutiveTask(ThreadTask... tasks) {
		List<ThreadTask> taskList = new ArrayList<ThreadTask>();
		for(ThreadTask task : tasks) {
			taskList.add(task);
		}
		this.tasks = Collections.unmodifiableCollection(taskList);
	}
	
	@Override
	public void execute() {
		for(ThreadTask task : tasks) {
			task.execute();
		}
	}

}
