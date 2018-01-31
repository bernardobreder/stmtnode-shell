package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class UIntTypeNativeNode extends TypeNativeNode {

	public static final UIntTypeNativeNode GET = new UIntTypeNativeNode();

	private UIntTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("uint");
	}

}
