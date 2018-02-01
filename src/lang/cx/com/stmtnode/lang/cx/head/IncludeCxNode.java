package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.head.HeadNativeNode;
import com.stmtnode.primitive.head.IncludeNativeNode;

public class IncludeCxNode extends HeadCxNode {

	public final Token path;

	public final boolean library;

	public IncludeCxNode(Token path, boolean library) {
		this.path = path;
		this.library = library;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new IncludeCxNode(path, library));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("#include ");
		output.write(library ? '<' : '\"');
		output.write(path);
		output.write(library ? '>' : '\"');
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HeadNativeNode toNative() {
		return new IncludeNativeNode(path, library);
	}

}
