package com.stmtnode.primitive.value.unary;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class GetNativeNode extends UnaryNativeNode {

	public final Token name;

	public GetNativeNode(ValueNativeNode left, Token name) {
		super(left);
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		left.writeToC(output);
		output.write('.');
		output.write(name);
	}

}
