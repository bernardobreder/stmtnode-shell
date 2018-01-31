package com.stmtnode.lang.cx.value.unary;

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

public class CastNode extends UnaryNode {

	public final TypeNode type;

	public CastNode(Token token, ValueNode left, TypeNode type) {
		super(token, left);
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new CastNode(token, linkNode(context, left), linkNode(context, type)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(".cast(");
		type.writeToSource(output);
		output.write(")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("((");
		type.writeToC(output);
		output.write(")");
		output.writeSpace();
		left.writeToC(output);
		output.write(")");
	}

}
