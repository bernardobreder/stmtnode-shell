package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxCodeOutput;
import com.stmtnode.module.CodeOutput;

public class StringNode extends ValueNode {

	public StringNode(Token token) {
		super(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(CodeOutput output) {
		output.write(token.word);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CxCodeOutput output) {
		output.write(token.word);
	}

}
