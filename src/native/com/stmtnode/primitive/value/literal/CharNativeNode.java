package com.stmtnode.primitive.value.literal;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class CharNativeNode extends ValueNativeNode {

	private int value;

	public CharNativeNode(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.writeInt((char) value);
	}

}
