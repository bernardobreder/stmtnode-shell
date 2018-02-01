package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.StmtNativeNode;
import com.stmtnode.primitive.stmt.StmtSetNativeNode;

public class DeferCxNode extends StmtCxNode {

	public final Token token;

	public final StmtCxNode command;

	public DeferCxNode(Token token, StmtCxNode command) {
		this.token = token;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		command.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		StmtCxNode command = linkNode(this.command, context);
		context.addBlock(command);
		return cast(new DeferCxNode(token, command));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("defer");
		output.writeSpace();
		command.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmtNativeNode toNative() {
		return new StmtSetNativeNode();
	}

}
