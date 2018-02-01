package com.stmtnode.primitive.value.unary;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class PosIncNativeNode extends UnaryNativeNode {

	public PosIncNativeNode(ValueNativeNode left) {
		super(left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		left.writeToC(output);
		output.write('+');
		output.write('+');
	}

}
