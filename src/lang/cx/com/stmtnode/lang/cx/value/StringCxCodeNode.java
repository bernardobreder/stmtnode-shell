package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;

public class StringCxCodeNode extends ValueCxCodeNode {

	public final Token token;

	public StringCxCodeNode(Token token) {
		this.token = token;
	}

}
