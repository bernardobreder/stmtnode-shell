package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class IncludeLibraryNode extends HeadNode {

	public final PathNode path;

	public IncludeLibraryNode(PathNode path) {
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new IncludeLibraryNode(path.link(context)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("#include <");
		path.writeToSource(output);
		output.write(">");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("#include <");
		path.writeToC(output);
		output.write(">");
	}

}
