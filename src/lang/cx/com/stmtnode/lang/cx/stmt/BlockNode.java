package com.stmtnode.lang.cx.stmt;

import java.util.List;

import com.stmtnode.lang.cx.CxCodeOutput;
import com.stmtnode.module.CodeOutput;

public class BlockNode extends StmtNode {

	public final List<StmtNode> nodes;

	public BlockNode(List<StmtNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(CodeOutput output) {
		output.writeLines(nodes, e -> e.writeToSource(output));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CxCodeOutput output) {
		output.writeLines(nodes, e -> e.writeToC(output));
	}

}
