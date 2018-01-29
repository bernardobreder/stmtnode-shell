package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.lang.cx.value.ValueNode;

public class DeclareValueNode extends StmtNode {

	public final Token token;

	public final TypeNode type;

	public final Token name;

	public final ValueNode value;

	public DeclareValueNode(Token token, TypeNode type, Token name, ValueNode value) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.value = value;
	}

}
