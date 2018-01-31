package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class UIntTypeNativeNode extends TypeNativeNode {

	public static final UIntTypeNativeNode GET = new UIntTypeNativeNode();

	private UIntTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("uint");
	}

}
