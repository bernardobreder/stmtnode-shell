package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.PointerTypeNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class PointerTypeCxNode extends WrapTypeCxNode {

	public PointerTypeCxNode(TypeCxNode type) {
		super(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new PointerTypeCxNode(linkNode(type, context)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		type.writeToSource(output);
		output.write("*");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeNativeNode toNative() {
		return new PointerTypeNativeNode(type.toNative());
	}

}
