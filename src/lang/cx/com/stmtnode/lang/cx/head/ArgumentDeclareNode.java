package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;

public class ArgumentDeclareNode extends HeadNode {

	public final Token name;

	public final TypeNode type;

	public ArgumentDeclareNode(Token name, TypeNode type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
	}

}
