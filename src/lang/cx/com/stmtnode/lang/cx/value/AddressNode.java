package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class AddressNode extends ValueNode {

	public final ValueNode left;

	public AddressNode(Token token, ValueNode left) {
		super(token);
		this.left = left;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("&");
		left.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("&");
		left.writeToC(output);
	}

}
