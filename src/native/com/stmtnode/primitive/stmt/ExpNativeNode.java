package com.stmtnode.primitive.stmt;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class ExpNativeNode extends StmtNativeNode {

	public final ValueNativeNode value;

	public ExpNativeNode(ValueNativeNode condition) {
		super();
		this.value = condition;
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
