package com.stmtnode.primitive.type;

import com.stmtnode.lang.cx.CCodeOutput;

public class SSizeTypeNativeNode extends TypeNativeNode {

	public static final SSizeTypeNativeNode GET = new SSizeTypeNativeNode();

	private SSizeTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("ssize_t");
	}

}
