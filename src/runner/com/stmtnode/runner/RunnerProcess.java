package com.stmtnode.runner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RunnerProcess {

	private CodeProcess process;

	public RunnerProcess(String content) throws IOException {
		File file = File.createTempFile("stmtnode-shell-", ".c");
		File execFile = File.createTempFile("stmtnode-shell-", ".exe");
		Files.write(file.toPath(), content.getBytes(UTF_8), WRITE, TRUNCATE_EXISTING, CREATE);
		StringBuilder compileBuffer = new StringBuilder();
		new CodeProcess("Build-C", line -> {
			compileBuffer.append(line).append("\n");
		}, exit -> {
			if (exit != 0) {
				System.err.println(content);
				System.err.println(compileBuffer.toString());
				return;
			}
			try {
				process = new CodeProcess("Exec-C", line -> {
					System.out.println(line);
				}, exit2 -> {
					System.out.println("Process exited: " + exit2);
				}, null).start(execFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, () -> {
			file.delete();
			execFile.delete();
		}).start("gcc", file.getAbsolutePath(), "-o", execFile.getAbsolutePath()).waitFor();
	}

	public void close() {
		if (process != null) {
			process.close();
		}
	}

}
