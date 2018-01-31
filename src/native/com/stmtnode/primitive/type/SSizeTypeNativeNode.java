package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class SSizeTypeNativeNode extends TypeNativeNode {

	public static final SSizeTypeNativeNode GET = new SSizeTypeNativeNode();

	private SSizeTypeNativeNode() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("ssize_t");
	}

}
