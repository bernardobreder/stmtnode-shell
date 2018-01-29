package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class UnitNode extends HeadNode {

	public final List<HeadNode> includes;

	public final List<HeadNode> nodes;

	public UnitNode(List<HeadNode> includes, List<HeadNode> nodes) {
		this.includes = includes;
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.writeLines(includes, e -> e.writeToSource(output));
		output.writeLines(nodes, e -> e.writeToSource(output));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeLines(includes, e -> e.writeToC(output));
		output.writeLines(nodes, e -> e.writeToC(output));
	}

}
