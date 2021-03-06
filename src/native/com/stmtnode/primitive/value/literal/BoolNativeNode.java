package com.stmtnode.primitive.value.literal;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class BoolNativeNode extends ValueNativeNode {

	public static final BoolNativeNode TRUE = new BoolNativeNode(true);

	public static final BoolNativeNode FALSE = new BoolNativeNode(false);

	private boolean value;

	private BoolNativeNode(boolean value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write(value ? "1" : "0");
	}

}
