package com.stmtnode.primitive.type;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;

public class IdTypeNativeNode extends TypeNativeNode {

	public final Token name;

	/**
	 * @param name
	 */
	public IdTypeNativeNode(Token name) {
		super();
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write(name);
	}

}
