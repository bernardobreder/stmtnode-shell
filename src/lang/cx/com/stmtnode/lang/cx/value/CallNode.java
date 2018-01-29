package com.stmtnode.lang.cx.value;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class CallNode extends ValueNode {

	public final ValueNode left;

	public final List<ValueNode> arguments;

	public CallNode(Token token, ValueNode left, List<ValueNode> arguments) {
		super(token);
		this.left = left;
		this.arguments = arguments;
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
