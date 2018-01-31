package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class DoubleTypeNativeNode extends TypeNativeNode {

	public static final DoubleTypeNativeNode GET = new DoubleTypeNativeNode();

	private DoubleTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("double");
	}

}
