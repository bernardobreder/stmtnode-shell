package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class CharTypeNativeNode extends TypeNativeNode {

	public static final CharTypeNativeNode GET = new CharTypeNativeNode();

	private CharTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("char");
	}

}
