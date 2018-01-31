package com.stmtnode.primitive.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;

public class IncludeNativeNode extends HeadNativeNode {

	public final Token path;

	public final boolean library;

	/**
	 * @param path
	 * @param library
	 */
	public IncludeNativeNode(Token path, boolean library) {
		super();
		this.path = path;
		this.library = library;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("#include ");
		output.write(library ? '<' : '\"');
		output.write(path);
		output.write(library ? '>' : '\"');
	}

}
