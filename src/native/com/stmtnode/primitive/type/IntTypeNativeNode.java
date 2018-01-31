package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class IntTypeNativeNode extends TypeNativeNode {

	public static final IntTypeNativeNode GET = new IntTypeNativeNode();

	private IntTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("int");
	}

}
