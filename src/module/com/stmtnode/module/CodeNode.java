package com.stmtnode.module;

import com.stmtnode.lang.cx.SourceCodeOutput;

public abstract class CodeNode extends Node {

	/**
	 * Realiza a escrita do objeto que o representa
	 * 
	 * @param output
	 */
	public abstract void writeToSource(SourceCodeOutput output);

}
