package com.stmtnode.primitive.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.type.TypeNativeNode;

public class StructFieldNativeNode extends HeadNativeNode {

	public final Token name;

	public final TypeNativeNode type;

	/**
	 * @param name
	 * @param type
	 */
	public StructFieldNativeNode(Token name, TypeNativeNode type) {
		super();
		this.name = name;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write(name);
		output.writeSpace();
		type.writeToC(output);
		output.write(';');
	}

}
