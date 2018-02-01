package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.nativeNodes;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.ReturnNativeNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;
import com.stmtnode.primitive.stmt.StmtSetNativeNode;

public class ReturnCxNode extends StmtCxNode {

	public final Token token;

	public final ValueCxNode value;

	public final List<StmtCxNode> dones;

	public ReturnCxNode(Token token, ValueCxNode value) {
		this(token, value, new ArrayList<>());
	}

	public ReturnCxNode(Token token, ValueCxNode value, List<StmtCxNode> dones) {
		this.token = token;
		this.value = value;
		this.dones = dones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		value.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new ReturnCxNode(token, linkNode(value, context), context.peekFunction()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("return ");
		value.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmtNativeNode toNative() {
		StmtSetNativeNode node = new StmtSetNativeNode();
		node.addAll(nativeNodes(dones));
		node.add(new ReturnNativeNode(value.toNative()));
		return node;
	}

}
