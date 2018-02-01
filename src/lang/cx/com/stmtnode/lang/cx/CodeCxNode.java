package com.stmtnode.lang.cx;

import com.stmtnode.module.CodeNode;
import com.stmtnode.primitive.NativeNode;

public abstract class CodeCxNode extends CodeNode {

	/**
	 * Realiza a escrita do objeto que o representa
	 *
	 * @param output
	 */
	public abstract void writeToSource(SourceCodeOutput output);

	public abstract NativeNode toNative();

}
