package com.stmtnode.watch;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import com.stmtnode.lang.compiler.Lexer;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxGrammar;
import com.stmtnode.module.CodeNode;
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
					.collect(Collectors.toMap(e -> e.getKey(), e -> compile(e.getKey(), e.getValue())));
		}
	}

	private CodeNode compile(Path path, String content) {
		Token[] tokens = new Lexer(path.toString(), content).execute();
		try {
			if (path.endsWith(".cx")) {
				return new CxGrammar(tokens).parseUnit();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new Main();
	}

}
