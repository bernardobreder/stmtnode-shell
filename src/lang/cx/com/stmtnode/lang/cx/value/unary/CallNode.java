package com.stmtnode.lang.cx.value.unary;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.linkNodes;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class CallNode extends UnaryNode {

	public final List<ValueNode> arguments;

	public CallNode(Token token, ValueNode left, List<ValueNode> arguments) {
		super(token, left);
		this.arguments = arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new CallNode(token, linkNode(context, left), linkNodes(context, arguments)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write("(");
		output.write(arguments, ", ", e -> e.writeToSource(output));
		output.write(")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		left.writeToC(output);
		output.write("(");
		output.write(arguments, ", ", e -> e.writeToC(output));
		output.write(")");
	}

}
