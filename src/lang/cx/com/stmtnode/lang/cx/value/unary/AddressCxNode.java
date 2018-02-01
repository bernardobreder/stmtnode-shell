package com.stmtnode.lang.cx.value.unary;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.PointerTypeCxNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.value.ValueNativeNode;
import com.stmtnode.primitive.value.unary.AddressNativeNode;

public class AddressCxNode extends UnaryCxNode {

	public AddressCxNode(Token token, ValueCxNode left) {
		this(token, null, left);
	}

	public AddressCxNode(Token token, TypeCxNode type, ValueCxNode left) {
		super(token, type, left);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		ValueCxNode left = linkNode(this.left, context);
		TypeCxNode type = new PointerTypeCxNode(left.type);
		return cast(new AddressCxNode(token, type, left));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("&");
		left.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValueNativeNode toNative() {
		return new AddressNativeNode(left.toNative());
	}

}
