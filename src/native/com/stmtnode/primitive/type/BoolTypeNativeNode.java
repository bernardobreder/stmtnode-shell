package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class BoolTypeNativeNode extends TypeNativeNode {

	public static final BoolTypeNativeNode GET = new BoolTypeNativeNode();

	private BoolTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("unsigned char");
	}

}
