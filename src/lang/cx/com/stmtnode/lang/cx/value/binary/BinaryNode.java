package com.stmtnode.lang.cx.value.binary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.lang.cx.value.unary.UnaryNode;

public abstract class BinaryNode extends UnaryNode {

	public final ValueCxNode right;

	public BinaryNode(Token token, ValueCxNode left, ValueCxNode right) {
		super(token, left);
		this.right = right;
	}

}
