package com.stmtnode.primitive.value;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.binary.BinaryNativeNode;

public class TernaryNativeNode extends BinaryNativeNode {

	public final ValueNativeNode center;

	/**
	 * @param left
	 * @param trueValue
	 * @param falseValue
	 */
	public TernaryNativeNode(ValueNativeNode left, ValueNativeNode trueValue, ValueNativeNode falseValue) {
		super(left, trueValue);
		this.center = falseValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write('(');
		left.writeToC(output);
		output.write(')');
		output.write('?');
		output.write('(');
		left.writeToC(output);
		output.write(')');
		output.write(':');
		output.write('(');
		right.writeToC(output);
		output.write(')');
	}

}
