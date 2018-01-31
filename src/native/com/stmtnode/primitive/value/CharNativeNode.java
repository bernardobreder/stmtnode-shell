package com.stmtnode.primitive.value;

import com.stmtnode.lang.cx.CCodeOutput;

public class CharNativeNode extends ValueNativeNode {

	private int value;

	public CharNativeNode(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeInt((char) value);
	}

}
