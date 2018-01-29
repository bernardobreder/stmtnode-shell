package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class IncludeLibraryNode extends HeadNode {

	public final PathNode path;

	public IncludeLibraryNode(PathNode path) {
		this.path = path;
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
