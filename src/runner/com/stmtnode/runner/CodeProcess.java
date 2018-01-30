package com.stmtnode.runner;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Stream;

public class CodeProcess {

	private final Consumer<String> output;

	private final IntConsumer finished;

	private final String name;

	private final Runnable done;

	private Thread errThread;

	private Thread inThread;

	private Thread consumerThread;

	private AtomicReference<Process> process;

	private Queue<String> queue;

	private AtomicBoolean closed;

	private AtomicBoolean doneFlag;

	private AtomicBoolean finishedFlag;

	public CodeProcess(String name, Consumer<String> output, IntConsumer finished, Runnable done) throws IOException {
		this.name = name;
		this.output = output;
		this.finished = finished;
		this.done = done;
	}

	public CodeProcess start(String command, String... arguments) throws IOException {
		String[] commands = Stream.concat(Stream.of(command), Arrays.stream(arguments)).toArray(String[]::new);
		closed = new AtomicBoolean(false);
		doneFlag = new AtomicBoolean(false);
		finishedFlag = new AtomicBoolean(false);
		process = new AtomicReference<>(new ProcessBuilder(commands).start());
		queue = new ConcurrentLinkedQueue<>();
		errThread = read(process.get().getErrorStream(), name + "-Error-Reader");
		inThread = read(process.get().getInputStream(), name + "-Input-Reader");
		consumerThread = createConsumer();
		return this;
	}

	protected Thread createConsumer() {
		Thread thread = new Thread(() -> {
			try {
				executeConsumer();
			} catch (InterruptedException e) {
			}
		}, name + "-Consumer");
		thread.start();
		return thread;
	}

	protected void executeConsumer() throws InterruptedException {
		while (!closed.get()) {
			if (queue.isEmpty()) {
				sleep();
			} else {
				output();
			}
		}
	}

	public void waitFor() {
		Process process;
		synchronized (this) {
			if (closed.get()) {
				throw new IllegalStateException();
			}
			process = this.process.get();
		}
		try {
			while (alive(process)) {
				if (queue.isEmpty()) {
					sleep();
				} else {
					output();
				}
			}
			errThread.join();
			inThread.join();
			output();
			finished(process);
		} catch (InterruptedException e) {
		} finally {
			this.closed.set(true);
			this.process.set(null);
			done();
		}
	}

	public void close() {
		Process process;
		synchronized (this) {
			if (closed.get()) {
				return;
			}
			process = this.process.get();
			closed.set(true);
			this.process.set(null);
		}
		try {
			destroy(process);
			while (alive(process)) {
				try {
					sleep();
				} catch (InterruptedException e) {
				}
			}
			errThread.interrupt();
			inThread.interrupt();
			output();
		} finally {
			done();
		}
	}

	protected void finished(Process process) {
		if (finished != null && finishedFlag.compareAndSet(false, true)) {
			finished.accept(process.exitValue());
		}
	}

	protected synchronized boolean alive(Process process) {
		return process.isAlive();
	}

	protected synchronized void destroy(Process process) {
		process.destroyForcibly();
	}

	protected void done() {
		if (done != null && doneFlag.compareAndSet(false, true)) {
			done.run();
		}
	}

	protected void sleep() throws InterruptedException {
		Thread.sleep(10);
	}

	protected synchronized void output() {
		if (output != null) {
			while (!queue.isEmpty()) {
				output.accept(queue.poll());
			}
		}
	}

	protected Thread read(InputStream input, String name) {
		Thread thread = new Thread(() -> {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, UTF_8));
				String line = reader.readLine();
				while (line != null) {
					queue.add(line);
					line = reader.readLine();
				}
			} catch (IOException e) {
			}
		}, name);
		thread.start();
		return thread;
	}

}
