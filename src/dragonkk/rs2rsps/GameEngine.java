package dragonkk.rs2rsps;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.util.BlockingExecutorService;
import dragonkk.rs2rsps.util.LegacyThreadFactory;

/**
 * Processes all game related logic.
 * 
 * @author Advocatus, major love to Graham for parts of this.
 */
public class GameEngine implements Runnable {

	/**
	 * Work service!
	 */
	public ExecutorService workService = Executors.newCachedThreadPool(new LegacyThreadFactory("WorkService"));

	/**
	 * A queue of pending tasks.
	 */
	private final BlockingQueue<ThreadTask> tasks = new LinkedBlockingQueue<ThreadTask>();

	/**
	 * The logic service.
	 */
	private final ScheduledExecutorService logicService = Executors
			.newScheduledThreadPool(10, new LegacyThreadFactory("LogicService"));

	/**
	 * The task service, used by <code>ParallelTask</code>s.
	 */
	public final BlockingExecutorService taskService = new BlockingExecutorService(
			Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors() + 1, new LegacyThreadFactory("TaskService")));
	

	/**
	 * Submits a new task which is processed on the logic thread as soon as
	 * possible.
	 * 
	 * @param task
	 *            The task to submit.
	 */
	public void pushTask(ThreadTask task) {
		tasks.offer(task);
	}

	public void submitWork(final Runnable runnable) {
		workService.submit(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} catch (Exception t) {
					t.printStackTrace();
				}
			}
		});
	}

	public boolean running = false;

	public Thread thread;

	@Override
	public void run() {
		while (running) {
			try {
				final ThreadTask task = this.tasks.take();
				submitLogic(new Runnable() {
					@Override
					public void run() {
						try {
							if(task.getClass().getSimpleName().equalsIgnoreCase("ConsecutiveTask")) {
								World.before = System.currentTimeMillis();
								task.execute();
								World.after = System.currentTimeMillis();
							} else {
								task.execute();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Submits a task to run in the logic service.
	 * 
	 * @param runnable
	 *            The runnable.
	 */
	public void submitLogic(final Runnable runnable) {
		logicService.submit(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} catch (Exception t) {
					t.printStackTrace();
				}
			}
		});
	}

	/**
	 * Submits a task to run in the parallel task service.
	 * 
	 * @param runnable
	 *            The runnable.
	 */
	public void submitTask(final Runnable runnable) {
		taskService.submit(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} catch (Exception t) {
					t.printStackTrace();
				}
			}
		});
	}

	/**
	 * Schedules a task to run in the logic service.
	 * 
	 * @param runnable
	 *            The runnable.
	 * @param delay
	 *            The delay.
	 * @param unit
	 *            The time unit.
	 * @return The <code>ScheduledFuture</code> of the scheduled logic.
	 */
	public ScheduledFuture<?> scheduleLogic(final Runnable runnable,
			long delay, TimeUnit unit) {
		return logicService.schedule(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} catch (Exception t) {
					t.printStackTrace();
				}
			}
		}, delay, unit);
	}
}