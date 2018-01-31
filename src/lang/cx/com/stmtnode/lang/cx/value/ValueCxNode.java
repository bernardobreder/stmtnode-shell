package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeNode;

public abstract class ValueCxNode extends NativeCodeNode {

	public final Token token;

	public ValueCxNode(Token token) {
		this.token = token;
	}

}
