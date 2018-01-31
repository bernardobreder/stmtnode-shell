package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class UInt64TypeNativeNode extends TypeNativeNode {

	public static final UInt64TypeNativeNode GET = new UInt64TypeNativeNode();

	private UInt64TypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("uint64_t");
	}

}
