package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.nativeNodes;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.BreakNativeNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;
import com.stmtnode.primitive.stmt.StmtSetNativeNode;

public class BreakCxNode extends StmtCxNode {

	public final Token token;

	public final List<StmtCxNode> dones;

	public BreakCxNode(Token token) {
		this(token, new ArrayList<>());
	}

	public BreakCxNode(Token token, List<StmtCxNode> dones) {
		this.token = token;
		this.dones = dones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		List<StmtCxNode> dones = context.peekBlock();
		return cast(new BreakCxNode(token, dones));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("break");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmtNativeNode toNative() {
		StmtSetNativeNode node = new StmtSetNativeNode();
		node.addAll(nativeNodes(dones));
		node.add(BreakNativeNode.GET);
		return node;
	}

}
