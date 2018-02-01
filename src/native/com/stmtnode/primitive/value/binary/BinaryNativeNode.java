package com.stmtnode.primitive.value.binary;

import com.stmtnode.primitive.value.ValueNativeNode;
import com.stmtnode.primitive.value.unary.UnaryNativeNode;

public abstract class BinaryNativeNode extends UnaryNativeNode {

	public ValueNativeNode right;

	/**
	 * @param left
	 * @param right
	 */
	public BinaryNativeNode(ValueNativeNode left, ValueNativeNode right) {
		super(left);
		this.right = right;
	}

}
