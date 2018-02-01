package com.stmtnode.primitive.value;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.type.TypeNativeNode;

public class SizeofTypeNativeNode extends ValueNativeNode {

	public final TypeNativeNode type;

	/**
	 * @param type
	 */
	public SizeofTypeNativeNode(TypeNativeNode type) {
		super();
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("sizeof");
		output.write('(');
		type.writeToC(output);
		output.write(')');
	}

}
