package com.stmtnode.lang.cx.value.binary;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.value.ValueNativeNode;
import com.stmtnode.primitive.value.binary.OrNativeNode;

public class OrCxNode extends BinaryCxNode {

	public OrCxNode(Token token, ValueCxNode left, ValueCxNode right) {
		this(token, null, left, right);
	}

	public OrCxNode(Token token, TypeCxNode type, ValueCxNode left, ValueCxNode right) {
		super(token, type, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		ValueCxNode left = linkNode(this.left, context);
		ValueCxNode right = linkNode(this.right, context);
		return cast(new OrCxNode(token, left.type, left, right));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(" or ");
		right.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValueNativeNode toNative() {
		return new OrNativeNode(left.toNative(), right.toNative());
	}

}
