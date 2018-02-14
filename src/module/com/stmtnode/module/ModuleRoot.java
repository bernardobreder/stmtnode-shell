package com.stmtnode.module;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModuleRoot {

	public final List<ModuleData> modules;

	public final Map<String, ModuleData> moduleMap;

	/**
	 * @param modules
	 */
	public ModuleRoot(List<ModuleData> modules) {
		super();
		this.modules = modules;
		this.moduleMap = modules.stream().collect(toMap(e -> e.name, e -> e));
	}

	public ModuleData getModule(String name) {
		return moduleMap.get(name);
	}

	public ModuleData computeModule(String name, Supplier<ModuleData> supplier) {
		ModuleData module = moduleMap.get(name);
		if (module == null) {
			module = supplier.get();
			modules.add(module);
			moduleMap.put(name, module);
		}
		return module;
	}

}
