package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.module.CodeOutput;

public class SizeofNode extends ValueNode {

	public final TypeNode type;

	public SizeofNode(Token token, TypeNode type) {
		super(token);
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(CodeOutput output) {
		output.write("sizeof(");
		type.writeToSource(output);
		output.write(")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CxCodeOutput output) {
		output.write("sizeof(");
		type.writeToSource(output);
		output.write(")");
	}

}
