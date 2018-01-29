package com.stmtnode.lang.cx.type;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class StructTypeNode extends TypeNode {

	public final Token name;

	public StructTypeNode(Token name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("struct ");
		output.write(name);
	}

}
