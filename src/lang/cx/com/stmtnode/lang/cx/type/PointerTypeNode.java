package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class PointerTypeNode extends WrapTypeNode {

	public PointerTypeNode(TypeCxNode type) {
		super(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new PointerTypeNode(linkNode(context, type)));
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
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.write("*");
	}

}
