package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;

public class IdentifierCxCodeNode extends ValueCxCodeNode {

	public final Token token;

	public IdentifierCxCodeNode(Token token) {
		this.token = token;
	}

}
