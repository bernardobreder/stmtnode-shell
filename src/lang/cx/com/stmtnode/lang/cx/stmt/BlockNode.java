package com.stmtnode.lang.cx.stmt;

import java.util.List;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class BlockNode extends StmtNode {

	public final List<StmtNode> nodes;

	public BlockNode(List<StmtNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("{");
		output.writeLine();
		output.incTab();
		output.writeTab();
		output.writeLines(nodes, e -> e.writeToSource(output));
		output.writeLine();
		output.decTab();
		output.writeTab();
		output.write("}");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("{");
		output.writeLine();
		output.incTab();
		output.writeTab();
		output.writeLines(nodes, e -> e.writeToC(output));
		output.writeLine();
		output.decTab();
		output.writeTab();
		output.write("}");
	}

}
