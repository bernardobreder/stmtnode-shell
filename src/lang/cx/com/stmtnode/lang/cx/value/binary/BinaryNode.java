package com.stmtnode.lang.cx.value.binary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.value.ValueNode;
import com.stmtnode.lang.cx.value.unary.UnaryNode;

public abstract class BinaryNode extends UnaryNode {

	public final ValueNode right;

	public BinaryNode(Token token, ValueNode left, ValueNode right) {
		super(token, left);
		this.right = right;
	}

}
