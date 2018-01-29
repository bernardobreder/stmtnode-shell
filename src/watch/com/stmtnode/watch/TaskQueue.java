package com.stmtnode.watch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bernardo Breder
 */
public class TaskQueue {

	private final ExecutorService queue = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public void submit() {

	}

	/**
	 * @param object
	 */
	public void execute(Runnable runnable) {
		queue.execute(runnable);
	}

	/**
	 *
	 */
	public void close() {
		queue.shutdown();
	}

}
