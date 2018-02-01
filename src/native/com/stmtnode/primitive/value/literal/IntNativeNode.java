package com.stmtnode.primitive.value.literal;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

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
