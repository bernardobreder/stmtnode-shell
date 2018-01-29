package com.stmtnode.lang.cx.value;

import java.util.List;

public class CallNode extends ValueNode {

	public final ValueNode left;

	public final List<ValueNode> arguments;

	public CallNode(ValueNode left, List<ValueNode> arguments) {
		this.left = left;
		this.arguments = arguments;
	}

}
