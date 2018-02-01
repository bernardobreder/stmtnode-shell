package com.stmtnode.primitive.value.unary;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class AddressNativeNode extends UnaryNativeNode {

	public AddressNativeNode(ValueNativeNode left) {
		super(left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write('&');
		left.writeToC(output);
	}

}
