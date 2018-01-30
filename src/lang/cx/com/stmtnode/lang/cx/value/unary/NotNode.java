package com.stmtnode.lang.cx.value.unary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;

public class NotNode extends ValueNode {

	public final ValueNode left;

	public NotNode(Token token, ValueNode left) {
		super(token);
		this.left = left;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("!");
		left.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("!");
		left.writeToC(output);
	}

}
