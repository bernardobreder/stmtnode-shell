package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNodes;

import java.util.List;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class UnitNode extends HeadCxNode {

	public final List<HeadCxNode> includes;

	public final List<HeadCxNode> nodes;

	public UnitNode(List<HeadCxNode> includes, List<HeadCxNode> nodes) {
		this.includes = includes;
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new UnitNode(linkNodes(context, includes), linkNodes(context, nodes)));
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
	public void writeToC(NativeCodeOutput output) {
		output.writeLines(includes, e -> e.writeToC(output));
		output.writeLine();
		output.writeLine();
		output.writeLines(nodes, e -> e.writeToC(output));
	}

}
