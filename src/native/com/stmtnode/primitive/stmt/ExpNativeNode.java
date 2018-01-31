package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.cx.CCodeOutput;
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
	public void writeToC(CCodeOutput output) {
		value.writeToC(output);
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
