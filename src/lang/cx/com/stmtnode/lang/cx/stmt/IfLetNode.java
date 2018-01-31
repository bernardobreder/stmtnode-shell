package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.lang.cx.value.ValueNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class IfLetNode extends StmtNode {

	public final Token token;

	public final TypeNode type;

	public final Token name;

	public final ValueNode value;

	public final ValueNode cond;

	public final StmtNode command;

	public IfLetNode(Token token, TypeNode type, Token name, ValueNode value, ValueNode cond, StmtNode command) {
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
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new IfLetNode(token, linkNode(context, type), name, linkNode(context, value), linkNode(context, cond), linkNode(context, command)));
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
	public void writeToC(CCodeOutput output) {
		output.write("{ ");
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		output.write(" = ");
		value.writeToC(output);
		output.write("; ");
		output.write("if (");
		cond.writeToC(output);
		output.write(")");
		output.writeSpace();
		command.writeToC(output);
		output.write(" }");
	}

}
