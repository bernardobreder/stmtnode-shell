package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.headNode;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.nativeNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.head.HeadNativeNode;
import com.stmtnode.primitive.head.StructFieldNativeNode;

public class StructFieldCxNode extends HeadCxNode {

	public final Token name;

	public final TypeCxNode type;

	public StructFieldCxNode(Token name, TypeCxNode type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		headNode(type, context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new StructFieldCxNode(name, linkNode(type, context)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HeadNativeNode toNative() {
		return new StructFieldNativeNode(name, nativeNode(type));
	}

}
