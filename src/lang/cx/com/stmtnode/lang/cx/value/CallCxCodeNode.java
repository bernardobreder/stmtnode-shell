package com.stmtnode.lang.cx.value;

import java.util.List;

public class CallCxCodeNode extends ValueCxCodeNode {

	public final ValueCxCodeNode left;

	public final List<ValueCxCodeNode> arguments;

	public CallCxCodeNode(ValueCxCodeNode left, List<ValueCxCodeNode> arguments) {
		this.left = left;
		this.arguments = arguments;
	}

}
