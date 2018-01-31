package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class CharsTypeNativeNode extends TypeNativeNode {

	public static final CharsTypeNativeNode GET = new CharsTypeNativeNode();

	private CharsTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("char*");
	}

}
