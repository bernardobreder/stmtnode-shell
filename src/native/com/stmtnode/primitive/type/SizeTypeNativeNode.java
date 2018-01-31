package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class SizeTypeNativeNode extends TypeNativeNode {

	public static final SizeTypeNativeNode GET = new SizeTypeNativeNode();

	private SizeTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("size_t");
	}

}
