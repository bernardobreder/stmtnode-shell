package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.BlockNativeNode;
import com.stmtnode.primitive.stmt.DeclareValueNativeNode;
import com.stmtnode.primitive.stmt.IfNativeNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;

public class IfLetCxNode extends StmtCxNode {

	public final Token token;

	public final TypeCxNode type;

	public final Token name;

	public final ValueCxNode value;

	public final ValueCxNode cond;

	public final StmtCxNode command;

	public IfLetCxNode(Token token, TypeCxNode type, Token name, ValueCxNode value, ValueCxNode cond, StmtCxNode command) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.value = value;
		this.cond = cond;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		type.head(context);
		value.head(context);
		cond.head(context);
		command.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new IfLetCxNode(token, linkNode(type, context), name, linkNode(value, context), linkNode(cond, context), linkNode(command, context)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("if let ");
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
		value.writeToSource(output);
		output.writeSpace();
		if (cond != null) {
			output.write(", ");
			cond.writeToSource(output);
			output.writeSpace();
		}
		command.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmtNativeNode toNative() {
		BlockNativeNode block = new BlockNativeNode();
		block.add(new DeclareValueNativeNode(name, type.toNative(), value.toNative()));
		block.add(new IfNativeNode(cond.toNative(), command.toNative()));
		return block;
	}

}
