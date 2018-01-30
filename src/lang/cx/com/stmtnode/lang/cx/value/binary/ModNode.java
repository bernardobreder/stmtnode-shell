package com.stmtnode.lang.cx.value.binary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;

public class ModNode extends BinaryNode {

	public ModNode(Token token, ValueNode left, ValueNode right) {
		super(token, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(" mod ");
		right.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("(");
		left.writeToC(output);
		output.write(") % (");
		right.writeToC(output);
		output.write(")");
	}

}