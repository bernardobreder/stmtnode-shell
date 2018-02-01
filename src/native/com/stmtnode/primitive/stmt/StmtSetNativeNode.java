package com.stmtnode.primitive.stmt;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.primitive.NativeCodeOutput;

public class StmtSetNativeNode extends StmtNativeNode {

	public final List<StmtNativeNode> nodes;

	/**
	 * Construtor padrão
	 */
	public StmtSetNativeNode() {
		this(new ArrayList<>());
	}

	/**
	 * @param nodes
	 */
	public StmtSetNativeNode(List<StmtNativeNode> nodes) {
		super();
		this.nodes = nodes;
	}

	public void add(StmtNativeNode node) {
		nodes.add(node);
	}

	public void addAll(List<StmtNativeNode> nodes) {
		this.nodes.addAll(nodes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.writeLines(nodes, e -> e.writeToC(output));
	}

}
