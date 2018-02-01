package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.CharsTypeNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class CharsTypeCxNode extends TypeCxNode {

	public static final CharsTypeCxNode GET = new CharsTypeCxNode();

	private CharsTypeCxNode() {
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
		output.write("chars");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeNativeNode toNative() {
		return CharsTypeNativeNode.GET;
	}

}
