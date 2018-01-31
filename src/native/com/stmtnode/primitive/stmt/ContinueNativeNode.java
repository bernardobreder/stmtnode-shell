package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.cx.CCodeOutput;

public class ContinueNativeNode extends StmtNativeNode {

	public static final ContinueNativeNode GET = new ContinueNativeNode();

	private ContinueNativeNode() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("continue");
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
