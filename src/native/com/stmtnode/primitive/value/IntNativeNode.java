package com.stmtnode.primitive.value;

import com.stmtnode.primitive.NativeCodeOutput;

public class IntNativeNode extends ValueNativeNode {

	private int value;

	public IntNativeNode(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.writeInt(value);
	}

}
