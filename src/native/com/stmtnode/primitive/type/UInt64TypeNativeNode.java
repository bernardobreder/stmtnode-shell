package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class UInt64TypeNativeNode extends TypeNativeNode {

	public static final UInt64TypeNativeNode GET = new UInt64TypeNativeNode();

	private UInt64TypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("uint64_t");
	}

}
