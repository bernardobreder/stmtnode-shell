package com.stmtnode.primitive.value.unary;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.type.TypeNativeNode;
import com.stmtnode.primitive.value.ValueNativeNode;

public class CastNativeNode extends UnaryNativeNode {

	public final TypeNativeNode type;

	public CastNativeNode(ValueNativeNode left, TypeNativeNode type) {
		super(left);
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write('(');
		output.write('(');
		type.writeToC(output);
		output.write(')');
		left.writeToC(output);
		output.write(')');
	}

}
