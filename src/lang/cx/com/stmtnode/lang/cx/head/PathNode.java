package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static java.util.stream.Collectors.joining;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class PathNode extends HeadCxNode {

	public final List<Token> tokens;

	public final Token token;

	public final String path;

	public PathNode(List<Token> tokens) {
		this.tokens = tokens;
		if (tokens.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.token = tokens.size() == 1 ? tokens.get(0) : tokens.get(0).join(tokens.get(tokens.size() - 1));
		this.path = tokens.stream().map(e -> e.word).collect(joining());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write(tokens, "", e -> output.write(e));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write(tokens, "", e -> output.write(e));
	}

}
