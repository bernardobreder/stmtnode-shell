package com.stmtnode.lang.cx.value.unary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.NodeContext;

public abstract class UnaryCxNode extends ValueCxNode {

	public final ValueCxNode left;

	public UnaryCxNode(Token token, ValueCxNode left) {
		this(token, null, left);
	}

	public UnaryCxNode(Token token, TypeCxNode type, ValueCxNode left) {
		super(token, type);
		this.left = left;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		left.head(context);
	}

}
