package com.stmtnode.primitive.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;
import com.stmtnode.primitive.stmt.StmtNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class FunctionNativeNode extends HeadNativeNode {

	public final Token name;

	public final TypeNativeNode type;

	public final List<ArgumentDeclareNativeNode> arguments;

	public final StmtNativeNode command;

	/**
	 * @param name
	 * @param type
	 * @param arguments
	 * @param command
	 */
	public FunctionNativeNode(Token name, TypeNativeNode type, List<ArgumentDeclareNativeNode> arguments, StmtNativeNode command) {
		super();
		this.name = name;
		this.type = type;
		this.arguments = arguments;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		output.writeSpaceBetweenNameAndSpace();
		output.write('(');
		output.writeLines(arguments, e -> e.writeToC(output));
		output.write(')');
		output.writeSpaceBetweenArgumentAndCommand();
		command.writeToC(output);
	}

}
