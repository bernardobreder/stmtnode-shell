package com.stmtnode.runner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.stream.Collectors.joining;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RunnerProcess {

	public RunnerProcess(String content) throws IOException {
		File file = File.createTempFile("stmtnode-shell-", ".c");
		File execFile = File.createTempFile("stmtnode-shell-", ".exe");
		try {
			Files.write(file.toPath(), content.getBytes(UTF_8), WRITE, TRUNCATE_EXISTING, CREATE);
			Process buildProcess = new ProcessBuilder("gcc", file.getAbsolutePath(), "-o", execFile.getAbsolutePath()).start();
			ConcurrentLinkedQueue<String> buildQueue = new ConcurrentLinkedQueue<>();
			if (execute(buildProcess, buildQueue) == 0) {
				Process execProcess = new ProcessBuilder(execFile.getAbsolutePath()).start();
				ConcurrentLinkedQueue<String> execQueue = new ConcurrentLinkedQueue<>();
				int execExitCode = execute(execProcess, execQueue);
				System.out.println("Finished execution: " + execExitCode);
				if (!execQueue.isEmpty()) {
					System.out.println(execQueue.stream().collect(joining("\n")));
				}
			} else {
				throw new IOException(buildQueue.stream().collect(joining("\n")));
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			file.delete();
			execFile.delete();
		}
	}

	protected int execute(Process process, ConcurrentLinkedQueue<String> queue) throws InterruptedException {
		Thread errThread = read(process, "Build-C-Error-Reader", queue, process.getErrorStream());
		Thread inThread = read(process, "Build-C-Input-Reader", queue, process.getInputStream());
		errThread.join();
		inThread.join();
		return process.waitFor();
	}

	protected Thread read(Process process, String name, Queue<String> queue, InputStream input) {
		Thread thread = new Thread(() -> {
			try {
				LineNumberReader reader = new LineNumberReader(new InputStreamReader(new BufferedInputStream(input), UTF_8));
				String line = reader.readLine();
				while (line != null) {
					queue.add(line);
					line = reader.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, name);
		thread.start();
		return thread;
	}

}
