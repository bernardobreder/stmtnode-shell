package com.stmtnode.lang.cx;

import com.stmtnode.module.CodeNode;

public abstract class CxCodeNode extends CodeNode {

	/**
	 * Realiza a escrita do objeto que o representa
	 * 
	 * @param output
	 */
	public abstract void writeToSource(SourceCodeOutput output);

	/**
	 * Realiza a escrita do objeto que o representa
	 * 
	 * @param output
	 */
	public abstract void writeToC(CCodeOutput output);

}
