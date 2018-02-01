package com.stmtnode.primitive.type;

public abstract class WrapTypeNativeNode extends TypeNativeNode {

	public final TypeNativeNode type;

	public WrapTypeNativeNode(TypeNativeNode type) {
		this.type = type;
	}

}
