package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.cx.CCodeOutput;

public class BreakNativeNode extends StmtNativeNode {

	public static final BreakNativeNode GET = new BreakNativeNode();

	private BreakNativeNode() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("break");
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
