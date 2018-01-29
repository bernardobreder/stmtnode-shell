package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class AssignNode extends ValueNode {

	public final ValueNode left;

	public final ValueNode value;

	public AssignNode(Token token, ValueNode left, ValueNode value) {
		super(token);
		this.left = left;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(" = ");
		value.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		left.writeToC(output);
		output.write(" = ");
		value.writeToC(output);
	}

}
