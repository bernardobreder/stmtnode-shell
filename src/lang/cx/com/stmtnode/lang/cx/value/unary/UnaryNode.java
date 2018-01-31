package com.stmtnode.lang.cx.value.unary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.value.ValueCxNode;

public abstract class UnaryNode extends ValueCxNode {

	public final ValueCxNode left;

	public UnaryNode(Token token, ValueCxNode left) {
		super(token);
		this.left = left;
	}

}
