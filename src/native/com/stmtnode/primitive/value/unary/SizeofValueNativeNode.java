package com.stmtnode.primitive.value.unary;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class SizeofValueNativeNode extends UnaryNativeNode {

	public SizeofValueNativeNode(ValueNativeNode left) {
		super(left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("sizeof");
		output.write('(');
		left.writeToC(output);
		output.write(')');
	}

}
