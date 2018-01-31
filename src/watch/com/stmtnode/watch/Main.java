package com.stmtnode.watch;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.stmtnode.lang.compiler.Grammar.SyntaxException;
import com.stmtnode.lang.compiler.Lexer;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxGrammar;
import com.stmtnode.lang.cx.head.HeadCxNode;
import com.stmtnode.lang.cx.head.IncludeLibraryNode;
import com.stmtnode.lang.cx.head.IncludeSourceNode;
import com.stmtnode.lang.cx.head.UnitNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.ModuleData;
import com.stmtnode.module.ModuleRoot;
import com.stmtnode.primitive.NativeCodeOutput;
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
			List<UnitNode> codes = compile(root).values().stream() //
					.filter(e -> e instanceof UnitNode) //
					.map(e -> (UnitNode) e) //
					.collect(toList());
			NativeCodeOutput coutput = new NativeCodeOutput();
			List<IncludeLibraryNode> librarys = codes.stream() //
					.flatMap(e -> e.includes.stream()) //
					.filter(e -> e instanceof IncludeLibraryNode) //
					.map(e -> (IncludeLibraryNode) e) //
					.collect(toList());
			List<IncludeSourceNode> includes = codes.stream() //
					.flatMap(e -> e.includes.stream()) //
					.filter(e -> e instanceof IncludeSourceNode) //
					.map(e -> (IncludeSourceNode) e) //
					.collect(toList());
			Set<String> libraryAdded = new HashSet<>();
			for (IncludeLibraryNode library : librarys) {
				String path = library.path.path;
				if (!libraryAdded.contains(path)) {
					libraryAdded.add(path);
					library.writeToC(coutput);
					coutput.writeLine();
				}
			}
			Set<String> includeAdded = new HashSet<>();
			for (IncludeSourceNode include : includes) {
				String path = include.path.word;
				if (!includeAdded.contains(path)) {
					includeAdded.add(path);
					include.writeToC(coutput);
					coutput.writeLine();
				}
			}
			for (UnitNode unit : codes) {
				for (HeadCxNode node : unit.nodes) {
					node.writeToC(coutput);
					coutput.writeLine();
				}
				coutput.writeLine();
			}
			System.err.println(coutput.toString());
			if (false) {
				if (runner != null) {
					runner.close();
				}
				try {
					runner = new RunnerProcess(coutput.toString());
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						if (runner != null) {
							runner.close();
						}
					}
				}));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	protected Map<Path, CodeNode> compile(ModuleRoot root) throws LinkException {
		boolean error = false;
		Map<Path, CodeNode> codes = new HashMap<>();
		List<ModuleData> modules = root.modules;
		for (ModuleData module : modules) {
			error |= compile(codes, module);
		}
		return error ? null : codes;
	}

	protected boolean compile(Map<Path, CodeNode> codes, ModuleData module) throws LinkException {
		boolean error = false;
		for (Entry<Path, String> entry : module.contents.entrySet()) {
			try {
				codes.put(entry.getKey(), compile(entry.getKey(), entry.getValue()));
			} catch (SyntaxException e) {
				error = true;
				System.err.println(e.getMessage());
			}
		}
		return error;
	}

	private CodeNode compile(Path path, String content) throws SyntaxException, LinkException {
		String name = path.toFile().getName();
		Token[] tokens = new Lexer(path.toString(), content).execute();
		if (name.endsWith(".cx")) {
			LinkContext context = new LinkContext();
			return new CxGrammar(tokens).parseUnit().link(context);
		}
		throw new IllegalArgumentException(path.toString());
	}

	public static void main(String[] args) {
		new Main();
	}

}
