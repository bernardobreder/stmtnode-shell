package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.StmtNativeNode;
import com.stmtnode.primitive.stmt.WhileNativeNode;

public class WhileCxNode extends StmtCxNode {

	public final Token token;

	public final ValueCxNode value;

	public final StmtCxNode command;

	public WhileCxNode(Token token, ValueCxNode value, StmtCxNode command) {
		this.token = token;
		this.value = value;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		value.head(context);
		command.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		context.pushBlock();
		try {
			return cast(new WhileCxNode(token, linkNode(value, context), linkNode(command, context)));
		} finally {
			context.popBlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("while ");
		value.writeToSource(output);
		output.writeSpace();
		command.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmtNativeNode toNative() {
		return new WhileNativeNode(value.toNative(), command.toNative());
	}

}
