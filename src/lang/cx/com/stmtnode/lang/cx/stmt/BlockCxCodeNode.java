package com.stmtnode.lang.cx.stmt;

import java.util.List;

public class BlockCxCodeNode extends StmtCxCodeNode {

	public final List<StmtCxCodeNode> nodes;

	public BlockCxCodeNode(List<StmtCxCodeNode> nodes) {
		this.nodes = nodes;
	}

}
