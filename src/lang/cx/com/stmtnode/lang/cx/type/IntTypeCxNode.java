package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.IntTypeNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class IntTypeCxNode extends TypeCxNode {

	public static final IntTypeCxNode GET = new IntTypeCxNode();

	private IntTypeCxNode() {
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
		return IntTypeNativeNode.GET;
	}

}
