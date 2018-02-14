package com.stmtnode.lang.cx.type;

public abstract class WrapTypeCxNode extends TypeCxNode {

	public final TypeCxNode type;

	public WrapTypeCxNode(TypeCxNode type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPointer() {
		return type.isPointer();
	}

}
