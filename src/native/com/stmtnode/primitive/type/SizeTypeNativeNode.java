package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class SizeTypeNativeNode extends TypeNativeNode {

	public static final SizeTypeNativeNode GET = new SizeTypeNativeNode();

	private SizeTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("size_t");
	}

}
