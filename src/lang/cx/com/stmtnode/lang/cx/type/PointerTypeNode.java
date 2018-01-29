package com.stmtnode.lang.cx.type;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class PointerTypeNode extends WrapTypeNode {

	public PointerTypeNode(TypeNode type) {
		super(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		type.writeToSource(output);
		output.write("*");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		type.writeToC(output);
		output.write("*");
	}

}
