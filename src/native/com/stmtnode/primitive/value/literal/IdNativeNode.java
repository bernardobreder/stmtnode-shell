package com.stmtnode.primitive.value.literal;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class IdNativeNode extends ValueNativeNode {

	private Token token;

	public IdNativeNode(Token token) {
		this.token = token;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write(token);
	}

}
