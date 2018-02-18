package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.headNodes;
import static com.stmtnode.module.Nodes.linkNodes;

import java.nio.file.Path;
import java.util.List;

import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.NativeNode;

public class UnitCxNode extends CodeCxNode {

	public final Path path;

	public final List<HeadCxNode> includes;

	public final List<HeadCxNode> nodes;

	public UnitCxNode(Path path, List<HeadCxNode> includes, List<HeadCxNode> nodes) {
		this.path = path;
		this.includes = includes;
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		headNodes(includes, context);
		headNodes(nodes, context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new UnitCxNode(path, linkNodes(includes, context), linkNodes(nodes, context)));
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
	public NativeNode toNative() {
		throw new RuntimeException();
	}

}
