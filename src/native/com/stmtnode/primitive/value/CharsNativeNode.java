package com.stmtnode.primitive.value;

import com.stmtnode.primitive.NativeCodeOutput;

public class CharsNativeNode extends ValueNativeNode {

	private String value;

	public CharsNativeNode(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write(value);
	}

}
