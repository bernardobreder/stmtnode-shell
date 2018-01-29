package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;

public class CastNode extends ValueNode {

	public final ValueNode left;

	public final TypeNode type;

	public CastNode(Token token, ValueNode left, TypeNode type) {
		super(token);
		this.left = left;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(".cast(");
		type.writeToSource(output);
		output.write(")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("((");
		type.writeToC(output);
		output.write(")");
		output.writeSpace();
		left.writeToC(output);
		output.write(")");
	}

}
