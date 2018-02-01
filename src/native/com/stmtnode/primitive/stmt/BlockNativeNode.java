package com.stmtnode.primitive.stmt;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.primitive.NativeCodeOutput;

public class BlockNativeNode extends StmtSetNativeNode {

	/**
	 * Construtor padrão
	 */
	public BlockNativeNode() {
		this(new ArrayList<>());
	}

	/**
	 * @param nodes
	 */
	public BlockNativeNode(List<StmtNativeNode> nodes) {
		super(nodes);
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
		super.writeToC(output);
		output.writeLine();
		output.decTab();
		output.writeTab();
		output.write("}");
	}

}
