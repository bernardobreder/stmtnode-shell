package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;

public class IncludeSourceNode extends HeadNode {

	public final Token path;

	public IncludeSourceNode(Token path) {
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("#include \"");
		output.write(path);
		output.write("\"");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("#include \"");
		output.write(path);
		output.write("\"");
	}

}
