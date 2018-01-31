package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class LongTypeNativeNode extends TypeNativeNode {

	public static final LongTypeNativeNode GET = new LongTypeNativeNode();

	private LongTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("long");
	}

}
