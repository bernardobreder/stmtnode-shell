package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.cx.CxCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;
import com.stmtnode.module.CodeOutput;

public class ExpNode extends StmtNode {

	public final ValueNode value;

	public ExpNode(ValueNode value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(CodeOutput output) {
		value.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CxCodeOutput output) {
		value.writeToC(output);
		output.write(";");
	}

}
