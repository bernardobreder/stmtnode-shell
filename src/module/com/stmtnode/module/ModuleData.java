package com.stmtnode.module;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ModuleData {

	public final String name;

	public final Map<Path, SourceData> contents = new HashMap<>();

	public ModuleData(String name) {
		this.name = name;
	}

}
