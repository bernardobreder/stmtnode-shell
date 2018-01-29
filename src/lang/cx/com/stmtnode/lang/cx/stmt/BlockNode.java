package com.stmtnode.lang.cx.stmt;

import java.util.List;

public class BlockNode extends StmtNode {

	public final List<StmtNode> nodes;

	public BlockNode(List<StmtNode> nodes) {
		this.nodes = nodes;
	}

}
