package com.stmtnode.primitive.stmt;

import java.util.List;

import com.stmtnode.primitive.NativeCodeOutput;

public class BlockNativeNode extends StmtNativeNode {

	public final List<StmtNativeNode> nodes;

	/**
	 * @param nodes
	 */
	public BlockNativeNode(List<StmtNativeNode> nodes) {
		super();
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
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
