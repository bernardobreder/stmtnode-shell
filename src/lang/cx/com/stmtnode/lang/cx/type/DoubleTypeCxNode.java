package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.DoubleTypeNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class DoubleTypeCxNode extends TypeCxNode {

	public static final DoubleTypeCxNode GET = new DoubleTypeCxNode();

	private DoubleTypeCxNode() {
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
		output.write("int");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeNativeNode toNative() {
		return DoubleTypeNativeNode.GET;
	}

}
