package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.primitive.value.ValueNativeNode;

public abstract class ValueCxNode extends CodeCxNode {

	public final Token token;

	public final TypeCxNode type;

	public ValueCxNode(Token token, TypeCxNode type) {
		this.token = token;
		this.type = type;
	}

	public abstract ValueNativeNode toNative();

}
