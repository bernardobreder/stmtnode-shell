package com.stmtnode.primitive.value.binary;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

/**
 * @author Tecgraf/PUC-Rio
 */
public class LowerNativeNode extends BinaryNativeNode {

	public LowerNativeNode(ValueNativeNode left, ValueNativeNode right) {
		super(left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		left.writeToC(output);
		output.writeSpace();
		output.write('<');
		output.writeSpace();
		right.writeToC(output);
	}

}
