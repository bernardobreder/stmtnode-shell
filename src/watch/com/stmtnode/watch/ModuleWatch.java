package com.stmtnode.watch;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.stmtnode.json.JsonObject;
import com.stmtnode.module.ModuleData;
import com.stmtnode.module.ModuleRoot;

public class ModuleWatch implements Runnable {

	private final Path dir;

	private final Consumer<ModuleRoot> consumer;

	private Thread thread;

	private boolean stopped;

	private int timestep = 100;

	private boolean changed;

	private Map<Path, byte[]> pathToBytes = new HashMap<>();

	private ModuleRoot root = new ModuleRoot(new ArrayList<>());

	public ModuleWatch(Path dir, Consumer<ModuleRoot> consumer) {
		this.dir = dir;
		this.consumer = consumer;
	}

	public void start() {
		if (thread != null) {
			throw new IllegalStateException();
		}
		thread = new Thread(this, this.getClass().getSimpleName() + "@" + Objects.hashCode(this));
		thread.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!stopped) {
			changed = false;
			root.modules.clear();
			try {
				read(dir);
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
			if (changed) {
				consumer.accept(root);
			}
			try {
				Thread.sleep(timestep);
			} catch (InterruptedException e) {
			}
		}
	}

	private void read(Path dir) throws IOException, ParseException {
		File[] files = dir.toFile().listFiles();
		if (files == null) {
			return;
		}
		Map<String, Path> map = Arrays.stream(files) //
				.filter(e -> !e.isHidden()) //
				.collect(toMap(e -> e.getName(), e -> e.toPath()));
		Path packagePath = map.get("package.json");
		if (packagePath != null) {
			JsonObject json = readPackage(packagePath);
			Optional<String> nameOpt = json.getAsString("name");
			if (nameOpt.isPresent()) {
				map.remove("package.json");
				ModuleData module = new ModuleData(nameOpt.get());
				List<Path> paths = map.values().stream().collect(toList());
				for (Path path : paths) {
					if (path.toFile().isFile()) {
						module.contents.put(path, readContent(path));
					}
				}
				root.modules.add(module);
			}
		} else {
			for (Path path : map.values()) {
				if (path.toFile().isDirectory()) {
					read(path);
				}
			}
		}
	}

	protected JsonObject readPackage(Path packagePath) throws IOException, ParseException {
		return new JsonObject(readContent(packagePath));
	}

	protected String readContent(Path packagePath) throws IOException {
		byte[] bytes = Files.readAllBytes(packagePath);
		byte[] lastBytes = pathToBytes.get(packagePath);
		if (lastBytes == null || !Arrays.equals(bytes, lastBytes)) {
			changed = true;
			pathToBytes.put(packagePath, bytes);
		}
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public void close() {
		stopped = true;
		thread.interrupt();
		try {
			thread.wait();
		} catch (InterruptedException e) {
		}
		stopped = true;
		thread = null;
	}

}
