package com.stmtnode.primitive.value;

import com.stmtnode.primitive.NativeCodeOutput;

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
