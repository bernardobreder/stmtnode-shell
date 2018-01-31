package com.stmtnode.primitive.stmt;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.type.TypeNativeNode;
import com.stmtnode.primitive.value.ValueNativeNode;

public class DeclareValueNativeNode extends StmtNativeNode {

	public final Token name;

	public final TypeNativeNode type;

	public final ValueNativeNode value;

	public DeclareValueNativeNode(Token name, TypeNativeNode type, ValueNativeNode value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		if (value != null) {
			output.writeSpace();
			output.write('=');
			output.writeSpace();
			value.writeToC(output);
		}
		output.writeSpaceBeforeCommon();
		output.write(';');
	}

}
