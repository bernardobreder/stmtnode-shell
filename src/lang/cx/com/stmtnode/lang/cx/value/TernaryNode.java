package com.stmtnode.lang.cx.value;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class TernaryNode extends ValueNode {

	public final ValueNode left;

	public final ValueNode trueValue;

	public final ValueNode falseValue;

	public TernaryNode(Token token, ValueNode left, ValueNode trueValue, ValueNode falseValue) {
		super(token);
		this.left = left;
		this.trueValue = trueValue;
		this.falseValue = falseValue;
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
