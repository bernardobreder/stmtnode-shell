package com.stmtnode.lang.cx.value.unary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.value.ValueNode;

public abstract class UnaryNode extends ValueNode {

	public final ValueNode left;

	public UnaryNode(Token token, ValueNode left) {
		super(token);
		this.left = left;
	}

}
