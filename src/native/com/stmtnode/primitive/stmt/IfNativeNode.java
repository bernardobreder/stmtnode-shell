package com.stmtnode.primitive.stmt;

import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.value.ValueNativeNode;

public class IfNativeNode extends StmtNativeNode {

	public final ValueNativeNode condition;

	public final StmtNativeNode command;

	public IfNativeNode(ValueNativeNode condition, StmtNativeNode command) {
		super();
		this.condition = condition;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("if");
		output.writeSpace();
		output.write('(');
		condition.writeToC(output);
		output.write(')');
		output.writeSpace();
		command.writeToC(output);
	}

}
