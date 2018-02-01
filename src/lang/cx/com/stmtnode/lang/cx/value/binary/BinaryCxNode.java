package com.stmtnode.lang.cx.value.binary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.lang.cx.value.unary.UnaryCxNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.NodeContext;

public abstract class BinaryCxNode extends UnaryCxNode {

	public final ValueCxNode right;

	public BinaryCxNode(Token token, ValueCxNode left, ValueCxNode right) {
		this(token, null, left, right);
	}

	public BinaryCxNode(Token token, TypeCxNode type, ValueCxNode left, ValueCxNode right) {
		super(token, type, left);
		this.right = right;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		super.head(context);
		right.head(context);
	}

}
