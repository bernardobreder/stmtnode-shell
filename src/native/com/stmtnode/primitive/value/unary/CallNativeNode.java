package com.stmtnode.primitive.value.unary;

import java.util.List;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class CallNativeNode extends UnaryNativeNode {

	public final List<ValueNativeNode> values;

	public CallNativeNode(ValueNativeNode left, List<ValueNativeNode> values) {
		super(left);
		this.values = values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		left.writeToC(output);
		output.write("(");
		output.write(values, ", ", e -> e.writeToC(output));
		output.write(")");
	}

}
