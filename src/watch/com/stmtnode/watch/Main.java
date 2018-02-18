package com.stmtnode.watch;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.stmtnode.lang.compiler.Grammar.SyntaxException;
import com.stmtnode.lang.compiler.Lexer;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxGrammar;
import com.stmtnode.lang.cx.head.HeadCxNode;
import com.stmtnode.lang.cx.head.UnitCxNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.Node;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.runner.RunnerProcess;

public class Main {

	static Log logger = new Log();

	private RunnerProcess runner;

	public Main(List<Path> paths) throws Exception {
		for (Entry<Path, UnitCxNode> entry : execute(paths).entrySet()) {
			Path path = Paths.get(entry.getKey().getParent().toString(), entry.getKey().toFile().getName() + ".c");
			UnitCxNode unitNode = entry.getValue();
			try (FileOutputStream out = new FileOutputStream(path.toFile())) {
				NativeCodeOutput coutput = new NativeCodeOutput();
				if (!unitNode.includes.isEmpty()) {
					for (HeadCxNode library : unitNode.includes) {
						library.toNative().writeToC(coutput);
						coutput.writeLine();
					}
					coutput.writeLine();
				}
				for (HeadCxNode library : unitNode.nodes) {
					library.toNative().writeToC(coutput);
					coutput.writeLine();
				}
				out.write(coutput.toString().getBytes(UTF_8));
			}
		}
	}

	/**
	 * @param paths
	 * @return
	 * @throws IOException
	 * @throws LinkException
	 */
	protected Map<Path, UnitCxNode> execute(List<Path> paths) throws IOException, LinkException {
		HashMap<Path, UnitCxNode> empty = new HashMap<>();
		Map<Path, UnitCxNode> nodes = compile(paths);
		if (nodes == null) {
			return empty;
		}
		boolean error = false;
		NodeContext context = new NodeContext();
		for (Node node : nodes.values()) {
			try {
				node.head(context);
			} catch (HeadException e) {
				error = true;
				System.err.println(e.getMessage());
			}
		}
		if (error) {
			return empty;
		}
		Map<Path, UnitCxNode> result = empty;
		for (Entry<Path, UnitCxNode> entry : nodes.entrySet()) {
			try {
				result.put(entry.getKey(), entry.getValue().link(context));
			} catch (LinkException e) {
				error = true;
				System.err.println(e.getMessage());
			}
		}
		if (error) {
			return empty;
		}
		return result;
	}

	protected Map<Path, UnitCxNode> compile(List<Path> paths) throws IOException, LinkException {
		Map<Path, UnitCxNode> nodes = new HashMap<>();
		boolean error = false;
		for (Path path : paths) {
			try {
				nodes.put(path, compile(path));
			} catch (SyntaxException e) {
				error = true;
				System.err.println(e.getMessage());
			}
		}
		if (error) {
			return null;
		}
		return nodes;
	}

	public UnitCxNode compile(Path path) throws IOException, SyntaxException {
		String name = path.toFile().getName();
		String content = new String(Files.readAllBytes(path));
		Token[] tokens = new Lexer(path.toString(), content).execute();
		if (name.endsWith(".cx")) {
			return new CxGrammar(path, tokens).parseUnit();
		}
		throw new IllegalArgumentException(path.toString());
	}

	public static void main(String[] args) throws Exception {
		List<Path> paths = new ArrayList<>();
		for (int n = 0; n < args.length; n++) {
			paths.add(Paths.get(args[n]));
		}
		if (paths.isEmpty()) {
			System.out.println("compile File...");
			return;
		}
		new Main(paths);
	}

}
