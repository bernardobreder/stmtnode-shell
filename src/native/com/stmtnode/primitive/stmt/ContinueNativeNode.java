package com.stmtnode.primitive.stmt;

import com.stmtnode.primitive.NativeCodeOutput;

public class ContinueNativeNode extends StmtNativeNode {

	public static final ContinueNativeNode GET = new ContinueNativeNode();

	private ContinueNativeNode() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("continue");
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
