package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class UnitNode extends HeadNode {

	public List<HeadNode> nodes;

	public UnitNode(List<HeadNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.writeLines(nodes, e -> e.writeToSource(output));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeLines(nodes, e -> e.writeToC(output));
	}

}
