package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.lang.cx.value.ValueNode;

public class DeclareArrayNode extends StmtNode {

	public final Token token;

	public final TypeNode type;

	public final Token name;

	public final ValueNode count;

	public DeclareArrayNode(Token token, TypeNode type, Token name, ValueNode count) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.count = count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("let local ");
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
		output.write("[");
		output.write(count.token);
		output.write("]");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		output.write("[");
		output.write(count.token);
		output.write("];");
	}

}
