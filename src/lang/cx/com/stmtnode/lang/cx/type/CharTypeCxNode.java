package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.CharTypeNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class CharTypeCxNode extends TypeCxNode {

	public static final CharTypeCxNode GET = new CharTypeCxNode();

	private CharTypeCxNode() {
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
		output.write("char");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeNativeNode toNative() {
		return CharTypeNativeNode.GET;
	}

}
