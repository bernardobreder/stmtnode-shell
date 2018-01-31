package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class DoubleTypeNativeNode extends TypeNativeNode {

	public static final DoubleTypeNativeNode GET = new DoubleTypeNativeNode();

	private DoubleTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("double");
	}

}
