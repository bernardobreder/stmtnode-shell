package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class WCharTypeNativeNode extends TypeNativeNode {

	public static final WCharTypeNativeNode GET = new WCharTypeNativeNode();

	private WCharTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("wchar_t");
	}

}
