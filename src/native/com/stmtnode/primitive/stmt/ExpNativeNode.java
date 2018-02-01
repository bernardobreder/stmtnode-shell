package com.stmtnode.primitive.stmt;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class ExpNativeNode extends StmtNativeNode {

	public final ValueNativeNode value;

	public ExpNativeNode(ValueNativeNode value) {
		super();
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		value.writeToC(output);
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
