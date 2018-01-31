package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class CharTypeNativeNode extends TypeNativeNode {

	public static final CharTypeNativeNode GET = new CharTypeNativeNode();

	private CharTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("char");
	}

}
