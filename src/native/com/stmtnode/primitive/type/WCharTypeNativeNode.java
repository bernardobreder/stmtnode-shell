package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class WCharTypeNativeNode extends TypeNativeNode {

	public static final WCharTypeNativeNode GET = new WCharTypeNativeNode();

	private WCharTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("wchar_t");
	}

}
