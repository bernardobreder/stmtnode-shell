package com.stmtnode.lang.cx.type;

public abstract class WrapTypeNode extends TypeCxNode {

	public final TypeCxNode type;

	public WrapTypeNode(TypeCxNode type) {
		this.type = type;
	}

}
