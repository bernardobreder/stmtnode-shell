package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class WhileNativeNode extends StmtNativeNode {

	public final ValueNativeNode condition;

	public final StmtNativeNode command;

	public WhileNativeNode(ValueNativeNode condition, StmtNativeNode command) {
		super();
		this.condition = condition;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("while");
		output.writeSpace();
		output.write('(');
		condition.writeToC(output);
		output.write(')');
		output.writeSpace();
		command.writeToC(output);
	}

}
