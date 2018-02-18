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
import com.stmtnode.primitive.stmt.DeclareValueNativeNode;
import com.stmtnode.primitive.stmt.IfNativeNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;
import com.stmtnode.primitive.stmt.StmtSetNativeNode;
import com.stmtnode.primitive.value.unary.NotNativeNode;

public class GuardLetCxNode extends StmtCxNode {

	public final Token token;

	public final DeclareValueCxNode declare;

	public final ValueCxNode cond;

	public final StmtCxNode command;

	public GuardLetCxNode(Token token, TypeCxNode type, Token name, ValueCxNode value, ValueCxNode cond, StmtCxNode command) {
		this.token = token;
		this.declare = new DeclareValueCxNode(token, type, name, value);
		this.cond = cond;
		this.command = command;
	}

	/**
	 * @param token
	 * @param declare
	 * @param cond
	 * @param command
	 */
	public GuardLetCxNode(Token token, DeclareValueCxNode declare, ValueCxNode cond, StmtCxNode command) {
		super();
		this.token = token;
		this.declare = declare;
		this.cond = cond;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		declare.head(context);
		cond.head(context);
		command.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		DeclareValueCxNode declare = linkNode(this.declare, context);
		ValueCxNode cond = linkNode(this.cond, context);
		StmtCxNode command = linkNode(this.command, context);
		return cast(new GuardLetCxNode(token, declare, cond, command));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("guard let ");
		declare.type.writeToSource(output);
		output.writeSpace();
		output.write(declare.name);
		declare.value.writeToSource(output);
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
		StmtSetNativeNode block = new StmtSetNativeNode();
		block.add(new DeclareValueNativeNode(declare.name, declare.type.toNative(), declare.value.toNative()));
		block.add(new IfNativeNode(new NotNativeNode(cond.toNative()), command.toNative()));
		return block;
	}

}
