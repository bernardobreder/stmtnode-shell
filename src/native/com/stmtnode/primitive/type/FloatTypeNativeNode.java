package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class FloatTypeNativeNode extends TypeNativeNode {

	public static final FloatTypeNativeNode GET = new FloatTypeNativeNode();

	private FloatTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("float");
	}

}
