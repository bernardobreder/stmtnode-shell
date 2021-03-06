package com.stmtnode.primitive.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.type.TypeNativeNode;

public class ArgumentDeclareNativeNode extends HeadNativeNode {

	public final Token name;

	public final TypeNativeNode type;

	/**
	 * @param name
	 * @param type
	 */
	public ArgumentDeclareNativeNode(Token name, TypeNativeNode type) {
		super();
		this.name = name;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
	}

}
