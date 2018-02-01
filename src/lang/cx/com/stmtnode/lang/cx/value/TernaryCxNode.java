package com.stmtnode.lang.cx.value;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.value.TernaryNativeNode;
import com.stmtnode.primitive.value.ValueNativeNode;

public class TernaryCxNode extends ValueCxNode {

	public final ValueCxNode left;

	public final ValueCxNode trueValue;

	public final ValueCxNode falseValue;

	public TernaryCxNode(Token token, ValueCxNode left, ValueCxNode trueValue, ValueCxNode falseValue) {
		this(token, null, left, trueValue, falseValue);
	}

	public TernaryCxNode(Token token, TypeCxNode type, ValueCxNode left, ValueCxNode trueValue, ValueCxNode falseValue) {
		super(token, type);
		this.left = left;
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		left.head(context);
		trueValue.head(context);
		falseValue.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		ValueCxNode left = linkNode(this.left, context);
		ValueCxNode trueValue = linkNode(this.trueValue, context);
		ValueCxNode falseValue = linkNode(this.falseValue, context);
		return cast(new TernaryCxNode(token, trueValue.type, left, trueValue, falseValue));
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
	public ValueNativeNode toNative() {
		return new TernaryNativeNode(left.toNative(), trueValue.toNative(), falseValue.toNative());
	}

}
