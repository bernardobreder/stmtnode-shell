package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxCodeNode;
import com.stmtnode.lang.cx.stmt.BlockCxCodeNode;
import com.stmtnode.lang.cx.type.TypeCxCodeNode;

public class FunctionCxCodeNode extends CxCodeNode {

	public final Token token;

	public final TypeCxCodeNode type;

	public final Token name;

	public final List<ArgumentDeclareCxCodeNode> arguments;

	public final BlockCxCodeNode block;

	public FunctionCxCodeNode(Token token, TypeCxCodeNode type, Token name, List<ArgumentDeclareCxCodeNode> arguments, BlockCxCodeNode block) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.arguments = arguments;
		this.block = block;
	}

}
