package dragonkk.rs2rsps.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LegacyThreadFactory.java
 * @author Mystic Flow for being so fucken smart and implementing a java class
 * Legacy614
 * Jul 13, 2011
 */
public class LegacyThreadFactory implements ThreadFactory {
	
	/**
	 * The thread group name
	 */
	private final String name;

	/**
	 * The <code>AtomicInteger</code> instance containing thread count
	 */
	private final AtomicInteger threadCount = new AtomicInteger();
	
	/**
	 * The constructor 
	 * @param name The name of the <code>ThreadGroup</code>.
	 */
	public LegacyThreadFactory(String name) {
		this.name = name;
	}
	
	/**
	 * Creates a new <code>Thread</code> with the name and thread count
	 * of the <code>ThreadGroup</code>.
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, new StringBuilder(name).append("-").append(threadCount.getAndIncrement()).toString());
		return thread;
	}
	
}