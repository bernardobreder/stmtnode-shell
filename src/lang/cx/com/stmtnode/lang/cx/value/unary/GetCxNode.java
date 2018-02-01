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
import com.stmtnode.primitive.value.unary.GetNativeNode;

public class GetCxNode extends UnaryCxNode {

	public final Token name;

	public GetCxNode(Token token, ValueCxNode left, Token name) {
		this(token, null, left, name);
	}

	public GetCxNode(Token token, TypeCxNode type, ValueCxNode left, Token name) {
		super(token, type, left);
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new GetCxNode(token, linkNode(left, context), name));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write(".");
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValueNativeNode toNative() {
		return new GetNativeNode(left.toNative(), name);
	}

}
