package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class IntTypeNativeNode extends TypeNativeNode {

	public static final IntTypeNativeNode GET = new IntTypeNativeNode();

	private IntTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("int");
	}

}
