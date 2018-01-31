package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class UInt32TypeNativeNode extends TypeNativeNode {

	public static final UInt32TypeNativeNode GET = new UInt32TypeNativeNode();

	private UInt32TypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("uint32_t");
	}

}
