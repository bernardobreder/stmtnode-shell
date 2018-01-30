package com.stmtnode.watch;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.stmtnode.lang.compiler.Grammar.SyntaxException;
import com.stmtnode.lang.compiler.Lexer;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.CxGrammar;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.head.UnitNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.ModuleData;
import com.stmtnode.module.ModuleRoot;
import com.stmtnode.runner.RunnerProcess;

public class Main {

	static Log logger = new Log();

	private RunnerProcess runner;

	public Main() {
		TaskQueue queue = new TaskQueue();
		Path inDir = Paths.get("mod");
		Path outDir = Paths.get("out");
		new ModuleWatch(inDir, this::execute).start();
	}

	private void execute(ModuleRoot root) {
		try {
			List<ModuleData> modules = root.modules;
			for (ModuleData module : modules) {
				Map<Path, CodeNode> codes = new HashMap<>();
				for (Entry<Path, String> entry : module.contents.entrySet()) {
					try {
						codes.put(entry.getKey(), compile(entry.getKey(), entry.getValue()));
					} catch (SyntaxException e) {
						System.err.println(e.getMessage());
					}
				}
			}
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					if (runner != null) {
						runner.close();
					}
				}
			}));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private CodeNode compile(Path path, String content) throws SyntaxException {
		String name = path.toFile().getName();
		Token[] tokens = new Lexer(path.toString(), content).execute();
		if (name.endsWith(".cx")) {
			UnitNode unit = new CxGrammar(tokens).parseUnit();

			SourceCodeOutput output = new SourceCodeOutput();
			unit.writeToSource(output);

			CCodeOutput coutput = new CCodeOutput();
			unit.writeToC(coutput);
			System.out.println(coutput.toString());

			if (runner != null) {
				runner.close();
			}
			try {
				runner = new RunnerProcess(coutput.toString());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}

			return unit;
		}
		throw new IllegalArgumentException(path.toString());
	}

	public static void main(String[] args) {
		new Main();
	}

}
