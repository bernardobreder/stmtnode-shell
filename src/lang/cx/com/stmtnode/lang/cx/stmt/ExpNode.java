package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.cx.value.ValueNode;

public class ExpNode extends StmtNode {

	public final ValueNode value;

	public ExpNode(ValueNode value) {
		this.value = value;
	}

}
