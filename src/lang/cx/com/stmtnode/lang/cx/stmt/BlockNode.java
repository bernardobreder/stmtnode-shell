package com.stmtnode.lang.cx.stmt;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class BlockNode extends StmtNode {

	public final List<StmtNode> nodes;

	public final List<StmtNode> dones = new ArrayList<>();

	public BlockNode(List<StmtNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		BlockNode node = new BlockNode(link(context, this.nodes));
		for (StmtNode child : node.nodes) {
			if (child instanceof ReturnNode) {
				break;
			} else if (child instanceof DeferNode) {
				DeferNode deferNode = (DeferNode) child;
				node.dones.add(deferNode.command);
			}
		}
		return cast(node);
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
		if (!dones.isEmpty()) {
			output.writeLine();
			output.writeTab();
			output.writeLines(dones, e -> e.writeToC(output));
		}
		output.writeLine();
		output.decTab();
		output.writeTab();
		output.write("}");
	}

}
