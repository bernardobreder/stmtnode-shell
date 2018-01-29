package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.lang.cx.value.ValueNode;
import com.stmtnode.module.CodeOutput;

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
	public void writeToSource(CodeOutput output) {
		output.write("let local ");
		type.writeToSource(output);
		output.writeSpace();
		output.write(name.word);
		output.write("[");
		output.write(count.token.word);
		output.write("]");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CxCodeOutput output) {
		type.writeToSource(output);
		output.writeSpace();
		output.write(name.word);
		output.write("[");
		output.write(count.token.word);
		output.write("];");
	}

}
