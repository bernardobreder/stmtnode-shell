package com.stmtnode.lang.cx.value.primitive;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.DoubleTypeCxNode;
import com.stmtnode.lang.cx.type.IntTypeCxNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.value.ValueNativeNode;
import com.stmtnode.primitive.value.literal.DoubleNativeNode;
import com.stmtnode.primitive.value.literal.IntNativeNode;

public class NumberCxNode extends ValueCxNode {

	public TypeCxNode type;

	public boolean dot;

	public int intValue;

	public double doubleValue;

	public NumberCxNode(Token token) {
		this(token, null, false, 0, 0.0);
	}

	/**
	 * @param token
	 * @param type
	 * @param doubleValue
	 * @param intValue
	 * @param value
	 */
	public NumberCxNode(Token token, TypeCxNode type, boolean doubleValue, int intValue, double value) {
		super(token, type);
		this.type = type;
		this.dot = doubleValue;
		this.intValue = intValue;
		this.doubleValue = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		boolean dot = token.word.contains(".");
		TypeCxNode type = dot ? DoubleTypeCxNode.GET : IntTypeCxNode.GET;
		double doubleValue = dot ? Double.valueOf(token.word) : 0;
		int intValue = dot ? 0 : Integer.valueOf(token.word);
		return cast(new NumberCxNode(token, type, dot, intValue, doubleValue));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValueNativeNode toNative() {
		return dot ? new DoubleNativeNode(doubleValue) : new IntNativeNode(intValue);
	}

}
