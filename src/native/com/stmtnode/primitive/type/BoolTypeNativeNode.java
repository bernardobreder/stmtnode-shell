package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class BoolTypeNativeNode extends TypeNativeNode {

	public static final BoolTypeNativeNode GET = new BoolTypeNativeNode();

	private BoolTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("unsigned char");
	}

}
