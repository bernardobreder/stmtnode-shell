package com.stmtnode.watch;

import static java.util.stream.Collectors.toMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import com.stmtnode.lang.compiler.Lexer;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.CxGrammar;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.head.UnitNode;
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
					.collect(toMap(e -> e.getKey(), e -> compile(e.getKey(), e.getValue())));
		}
	}

	private CodeNode compile(Path path, String content) {
		String name = path.toFile().getName();
		Token[] tokens = new Lexer(path.toString(), content).execute();
		try {
			if (name.endsWith(".cx")) {
				UnitNode unit = new CxGrammar(tokens).parseUnit();

				SourceCodeOutput output = new SourceCodeOutput();
				unit.writeToSource(output);
				System.out.println(output.toString());

				CCodeOutput coutput = new CCodeOutput();
				unit.writeToC(coutput);
				System.err.println(coutput.toString());

				return unit;
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		throw new IllegalArgumentException(path.toString());
	}

	public static void main(String[] args) {
		new Main();
	}

}
