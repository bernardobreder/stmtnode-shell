package com.stmtnode.watch;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.stmtnode.module.ModuleData;
import com.stmtnode.module.ModuleRoot;

public class Main {

	static Log logger = new Log();

	public Main() {
		TaskQueue queue = new TaskQueue();
		Path inDir = Paths.get("mod");
		Path outDir = Paths.get("out");
		new ModuleWatch(inDir, this::execute).start();
	}

	private void execute(ModuleRoot root) {
		List<ModuleData> modules = root.modules;
		for (ModuleData module : modules) {
			module.contents.entrySet().stream() //
					.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
