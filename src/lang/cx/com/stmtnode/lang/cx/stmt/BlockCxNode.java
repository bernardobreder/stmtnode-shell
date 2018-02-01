package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNodes;
import static com.stmtnode.module.Nodes.nativeNodes;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.BlockNativeNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;

public class BlockCxNode extends StmtCxNode {

	public final List<StmtCxNode> nodes;

	public final List<StmtCxNode> dones = new ArrayList<>();

	public BlockCxNode(List<StmtCxNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		for (StmtCxNode node : nodes) {
			node.head(context);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		BlockCxNode node = new BlockCxNode(linkNodes(this.nodes, context));
		for (StmtCxNode child : node.nodes) {
			if (child instanceof ReturnCxNode) {
				break;
			} else if (child instanceof DeferCxNode) {
				DeferCxNode deferNode = (DeferCxNode) child;
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
	public StmtNativeNode toNative() {
		BlockNativeNode node = new BlockNativeNode();
		node.addAll(nativeNodes(nodes));
		node.addAll(nativeNodes(dones));
		return node;
	}

}
