package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;

public class NumberNode extends ValueNode {

	public final Token token;

	public NumberNode(Token token) {
		this.token = token;
	}

}
