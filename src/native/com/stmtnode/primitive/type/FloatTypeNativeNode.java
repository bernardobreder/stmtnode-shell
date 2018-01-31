package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class FloatTypeNativeNode extends TypeNativeNode {

	public static final FloatTypeNativeNode GET = new FloatTypeNativeNode();

	private FloatTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("float");
	}

}
