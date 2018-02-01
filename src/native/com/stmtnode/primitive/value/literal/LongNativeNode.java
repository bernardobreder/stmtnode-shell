package com.stmtnode.primitive.value.literal;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class LongNativeNode extends ValueNativeNode {

	private long value;

	public LongNativeNode(long value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.writeLong(value);
	}

}
