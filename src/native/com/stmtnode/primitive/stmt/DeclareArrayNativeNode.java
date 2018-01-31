package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.primitive.type.TypeNativeNode;
import com.stmtnode.primitive.value.ValueNativeNode;

public class DeclareArrayNativeNode extends StmtNativeNode {

	public final Token name;

	public final TypeNativeNode type;

	public final ValueNativeNode length;

	public DeclareArrayNativeNode(Token name, TypeNativeNode type, ValueNativeNode length) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		output.writeSpace();
		output.write('[');
		output.writeSpaceBeforeArray();
		length.writeToC(output);
		output.writeSpaceAfterArray();
		output.write(']');
		output.writeSpaceBeforeCommon();
		output.write(';');
	}
}
