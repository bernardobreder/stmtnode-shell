package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class UnknownTypeNativeNode extends TypeNativeNode {

	public final String name;

	public UnknownTypeNativeNode(String name) {
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
