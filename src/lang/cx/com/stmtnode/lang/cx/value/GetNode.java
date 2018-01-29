package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class GetNode extends ValueNode {

	public final ValueNode left;

	public final Token name;

	public GetNode(Token token, ValueNode left, Token name) {
		super(token);
		this.left = left;
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(".");
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		left.writeToC(output);
		output.write(".");
		output.write(name);
	}

}
