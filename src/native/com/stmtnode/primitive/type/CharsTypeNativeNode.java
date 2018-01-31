package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class CharsTypeNativeNode extends TypeNativeNode {

	public static final CharsTypeNativeNode GET = new CharsTypeNativeNode();

	private CharsTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("char*");
	}

}
