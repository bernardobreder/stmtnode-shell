package com.stmtnode.primitive.value;

import com.stmtnode.lang.cx.CCodeOutput;

public class IntNativeNode extends ValueNativeNode {

	private int value;

	public IntNativeNode(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeInt(value);
	}

}
