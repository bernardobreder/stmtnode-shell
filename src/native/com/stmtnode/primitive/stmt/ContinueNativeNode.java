package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.primitive.NativeNode;

public class ContinueNativeNode extends NativeNode {

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
