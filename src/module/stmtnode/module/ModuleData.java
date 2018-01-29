package stmtnode.module;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ModuleData {

	public String name;

	public final Map<Path, String> contents = new HashMap<>();

	public ModuleData(String name) {
		this.name = name;
	}

}
