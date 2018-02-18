package com.stmtnode.watch;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.stmtnode.lang.compiler.Grammar.SyntaxException;
import com.stmtnode.lang.compiler.Lexer;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxGrammar;
import com.stmtnode.lang.cx.head.HeadCxNode;
import com.stmtnode.lang.cx.head.IncludeCxNode;
import com.stmtnode.lang.cx.head.UnitCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.ModuleData;
import com.stmtnode.module.ModuleRoot;
import com.stmtnode.module.NodeContext;
import com.stmtnode.module.SourceData;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.runner.RunnerProcess;

public class OldMain {

	static Log logger = new Log();

	private RunnerProcess runner;

	public OldMain() {
		TaskQueue queue = new TaskQueue();
		Path inDir = Paths.get("mod");
		Path outDir = Paths.get("out");
		new ModuleWatch(inDir, this::execute).start();
	}

	private void execute(ModuleRoot root) {
		try {
			List<UnitCxNode> codes = compile(root).values().stream() //
					.filter(e -> e instanceof UnitCxNode) //
					.map(e -> (UnitCxNode) e) //
					.collect(toList());
			NativeCodeOutput coutput = new NativeCodeOutput();
			List<IncludeCxNode> librarys = codes.stream() //
					.flatMap(e -> e.includes.stream()) //
					.filter(e -> e instanceof IncludeCxNode) //
					.map(e -> (IncludeCxNode) e) //
					.filter(e -> e.library) //
					.collect(toList());
			List<IncludeCxNode> includes = codes.stream() //
					.flatMap(e -> e.includes.stream()) //
					.filter(e -> e instanceof IncludeCxNode) //
					.map(e -> (IncludeCxNode) e) //
					.filter(e -> !e.library) //
					.collect(toList());
			Set<String> libraryAdded = new HashSet<>();
			for (IncludeCxNode library : librarys) {
				String path = library.path.word;
				if (!libraryAdded.contains(path)) {
					libraryAdded.add(path);
					library.toNative().writeToC(coutput);
					coutput.writeLine();
				}
			}
			Set<String> includeAdded = new HashSet<>();
			for (IncludeCxNode include : includes) {
				String path = include.path.word;
				if (!includeAdded.contains(path)) {
					includeAdded.add(path);
					include.toNative().writeToC(coutput);
					coutput.writeLine();
				}
			}
			for (UnitCxNode unit : codes) {
				for (HeadCxNode node : unit.nodes) {
					node.toNative().writeToC(coutput);
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
		List<ModuleData> modules = root.modules;
		for (ModuleData module : modules) {
			error |= compile(module);
		}
		if (error) {
			return null;
		}
		NodeContext context = new NodeContext();
		for (ModuleData module : root.modules) {
			for (SourceData source : module.contents.values()) {
				try {
					source.node.head(context);
				} catch (HeadException e) {
					error = true;
					System.err.println(e.getMessage());
				}
			}
		}
		if (error) {
			return null;
		}
		for (ModuleData module : root.modules) {
			for (SourceData source : module.contents.values()) {
				try {
					source.node = source.node.link(context);
				} catch (LinkException e) {
					error = true;
					System.err.println(e.getMessage());
				}
			}
		}
		if (error) {
			return null;
		}
		Map<Path, CodeNode> codes = new HashMap<>();
		for (ModuleData module : root.modules) {
			for (SourceData source : module.contents.values()) {
				codes.put(source.path, source.node);
			}
		}
		return codes;
	}

	protected boolean compile(ModuleData module) throws LinkException {
		boolean error = false;
		for (SourceData source : module.contents.values()) {
			if (source.node == null) {
				try {
					source.node = compile(source);
				} catch (SyntaxException e) {
					error = true;
					System.err.println(e.getMessage());
				}
			}
		}
		return error;
	}

	private CodeNode compile(SourceData source) throws SyntaxException {
		String name = source.path.toFile().getName();
		Token[] tokens = new Lexer(source.path.toString(), source.content).execute();
		if (name.endsWith(".cx")) {
			return new CxGrammar(null, tokens).parseUnit();
		}
		throw new IllegalArgumentException(source.path.toString());
	}

	public static void main(String[] args) {
		new OldMain();
	}

}
