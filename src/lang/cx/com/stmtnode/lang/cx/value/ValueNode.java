package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxCodeNode;

public abstract class ValueNode extends CxCodeNode {

	public final Token token;

	public ValueNode(Token token) {
		this.token = token;
	}

}
