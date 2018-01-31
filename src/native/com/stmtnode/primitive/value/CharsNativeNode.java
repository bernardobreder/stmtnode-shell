package com.stmtnode.primitive.value;

import com.stmtnode.lang.cx.CCodeOutput;

public class CharsNativeNode extends ValueNativeNode {

	private String value;

	public CharsNativeNode(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write(value);
	}

}
