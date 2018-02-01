package com.stmtnode.primitive.value.unary;

import com.stmtnode.primitive.value.ValueNativeNode;

public abstract class UnaryNativeNode extends ValueNativeNode {

	public ValueNativeNode left;

	/**
	 * @param left
	 */
	public UnaryNativeNode(ValueNativeNode left) {
		super();
		this.left = left;
	}

}
