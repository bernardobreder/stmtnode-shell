package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.stmt.BlockNode;
import com.stmtnode.lang.cx.type.TypeNode;

public class FunctionNode extends HeadNode {

	public final Token token;

	public final TypeNode type;

	public final Token name;

	public final List<ArgumentDeclareNode> arguments;

	public final BlockNode block;

	public FunctionNode(Token token, TypeNode type, Token name, List<ArgumentDeclareNode> arguments, BlockNode block) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.arguments = arguments;
		this.block = block;
	}

}
