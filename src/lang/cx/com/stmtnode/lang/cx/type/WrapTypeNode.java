package com.stmtnode.lang.cx.type;

public abstract class WrapTypeNode extends TypeNode {

	public final TypeNode type;

	public WrapTypeNode(TypeNode type) {
		this.type = type;
	}

}
