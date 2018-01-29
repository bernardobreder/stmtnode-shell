package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;

public class StringNode extends ValueNode {

	public final Token token;

	public StringNode(Token token) {
		this.token = token;
	}

}
