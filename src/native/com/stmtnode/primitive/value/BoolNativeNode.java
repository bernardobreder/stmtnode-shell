package com.stmtnode.primitive.value;

import com.stmtnode.lang.cx.CCodeOutput;

public class BoolNativeNode extends ValueNativeNode {

	private boolean value;

	public BoolNativeNode(boolean value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeInt(value ? 1 : 0);
	}

}
