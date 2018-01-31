package com.stmtnode.primitive.stmt;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class ReturnNativeNode extends StmtNativeNode {

	public final ValueNativeNode value;

	public ReturnNativeNode(ValueNativeNode condition) {
		super();
		this.value = condition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("return");
		output.writeSpace();
		value.writeToC(output);
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
