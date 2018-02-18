package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class VoidTypeNativeNode extends TypeNativeNode {

	public static final VoidTypeNativeNode GET = new VoidTypeNativeNode();

	private VoidTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("void");
	}

}
