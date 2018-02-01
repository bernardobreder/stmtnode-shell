package com.stmtnode.lang.cx.value.unary;

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
import com.stmtnode.primitive.value.unary.PreDecNativeNode;

public class PreDecCxNode extends UnaryCxNode {

	public PreDecCxNode(Token token, ValueCxNode left) {
		this(token, null, left);
	}

	public PreDecCxNode(Token token, TypeCxNode type, ValueCxNode left) {
		super(token, type, left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		ValueCxNode left = linkNode(this.left, context);
		return cast(new PreIncCxNode(token, left.type, left));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("--");
		left.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValueNativeNode toNative() {
		return new PreDecNativeNode(left.toNative());
	}

}
