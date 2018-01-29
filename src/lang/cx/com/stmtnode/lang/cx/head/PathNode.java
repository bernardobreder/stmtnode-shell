package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;

public class PathNode extends HeadNode {

	public final List<Token> tokens;

	public final Token token;

	public PathNode(List<Token> tokens) {
		this.tokens = tokens;
		if (tokens.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.token = tokens.size() == 1 ? tokens.get(0) : tokens.get(0).join(tokens.get(tokens.size() - 1));
	}

}
