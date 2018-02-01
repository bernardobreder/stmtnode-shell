package com.stmtnode.primitive.value.literal;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

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
