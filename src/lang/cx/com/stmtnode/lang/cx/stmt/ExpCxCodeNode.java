package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.cx.value.ValueCxCodeNode;

public class ExpCxCodeNode extends StmtCxCodeNode {

	public final ValueCxCodeNode value;

	public ExpCxCodeNode(ValueCxCodeNode value) {
		this.value = value;
	}

}
