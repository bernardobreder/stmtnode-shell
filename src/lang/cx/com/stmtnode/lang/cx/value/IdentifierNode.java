package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;

public class IdentifierNode extends ValueNode {

	public final Token token;

	public IdentifierNode(Token token) {
		this.token = token;
	}

}
