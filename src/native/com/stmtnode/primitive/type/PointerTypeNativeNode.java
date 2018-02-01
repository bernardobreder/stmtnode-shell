package com.stmtnode.primitive.type;

import com.stmtnode.primitive.NativeCodeOutput;

public class PointerTypeNativeNode extends WrapTypeNativeNode {

	public PointerTypeNativeNode(TypeNativeNode type) {
		super(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPointer() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.write('*');
	}

}
