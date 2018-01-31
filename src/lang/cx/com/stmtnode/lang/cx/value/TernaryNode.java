package com.stmtnode.lang.cx.value;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class TernaryNode extends ValueCxNode {

	public final ValueCxNode left;

	public final ValueCxNode trueValue;

	public final ValueCxNode falseValue;

	public TernaryNode(Token token, ValueCxNode left, ValueCxNode trueValue, ValueCxNode falseValue) {
		super(token);
		this.left = left;
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new TernaryNode(token, linkNode(context, left), linkNode(context, trueValue), linkNode(context, falseValue)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(" ? ");
		trueValue.writeToSource(output);
		output.write(" : ");
		falseValue.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("(");
		left.writeToC(output);
		output.write(") ? (");
		trueValue.writeToC(output);
		output.write(") : (");
		falseValue.writeToC(output);
		output.write(")");
	}

}
