package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class WhileNode extends StmtCxNode {

	public final Token token;

	public final ValueCxNode value;

	public final StmtCxNode command;

	public WhileNode(Token token, ValueCxNode value, StmtCxNode command) {
		this.token = token;
		this.value = value;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		context.pushBlock();
		try {
			return cast(new WhileNode(token, linkNode(context, value), linkNode(context, command)));
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
	public void writeToC(NativeCodeOutput output) {
		output.write("while (");
		value.writeToC(output);
		output.write(")");
		output.writeSpace();
		command.writeToC(output);
	}

}
