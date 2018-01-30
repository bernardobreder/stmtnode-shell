package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;

public class WhileNode extends StmtNode {

	public final Token token;

	public final ValueNode value;

	public final StmtNode command;

	public WhileNode(Token token, ValueNode value, StmtNode command) {
		this.token = token;
		this.value = value;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("while ");
		value.writeToSource(output);
		output.writeSpace();
		command.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("while (");
		value.writeToC(output);
		output.write(")");
		output.writeSpace();
		command.writeToC(output);
	}

}
