package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class LongTypeNativeNode extends TypeNativeNode {

	public static final LongTypeNativeNode GET = new LongTypeNativeNode();

	private LongTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("long");
	}

}
