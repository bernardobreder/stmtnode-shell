package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CxCodeNode;
import com.stmtnode.lang.cx.type.TypeCxCodeNode;

public class ArgumentDeclareCxCodeNode extends CxCodeNode {

	public final Token name;

	public final TypeCxCodeNode type;

	public ArgumentDeclareCxCodeNode(Token name, TypeCxCodeNode type) {
		this.name = name;
		this.type = type;
	}

}
