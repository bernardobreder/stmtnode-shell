package com.stmtnode.lang.cx.value.primitive;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;

public class BooleanNode extends ValueNode {

	private final Boolean value;

	/**
	 * @param token
	 * @param value
	 */
	public BooleanNode(Token token, Boolean value) {
		super(token);
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write(value.booleanValue() ? "1" : "0");
	}

}
