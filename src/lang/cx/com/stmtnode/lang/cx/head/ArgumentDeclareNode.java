package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.type.TypeNode;

public class ArgumentDeclareNode extends HeadNode {

	public final Token name;

	public final TypeNode type;

	public ArgumentDeclareNode(Token name, TypeNode type) {
		this.name = name;
		this.type = type;
	}

}
