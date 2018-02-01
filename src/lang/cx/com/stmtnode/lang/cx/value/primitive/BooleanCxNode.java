package com.stmtnode.lang.cx.value.primitive;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.BoolTypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.value.ValueNativeNode;
import com.stmtnode.primitive.value.literal.BoolNativeNode;

public class BooleanCxNode extends ValueCxNode {

	public final Boolean value;

	/**
	 * @param token
	 * @param value
	 */
	public BooleanCxNode(Token token, Boolean value) {
		super(token, BoolTypeCxNode.GET);
		this.value = value;
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
		return cast(this);
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
		return value.booleanValue() ? BoolNativeNode.TRUE : BoolNativeNode.FALSE;
	}

}
