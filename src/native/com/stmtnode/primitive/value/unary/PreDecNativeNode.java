package com.stmtnode.primitive.value.unary;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class PreDecNativeNode extends UnaryNativeNode {

	public PreDecNativeNode(ValueNativeNode left) {
		super(left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write('-');
		output.write('-');
		left.writeToC(output);
	}

}
