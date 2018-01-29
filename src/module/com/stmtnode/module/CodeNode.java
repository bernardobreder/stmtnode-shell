package com.stmtnode.module;

public abstract class CodeNode extends Node {

	/**
	 * Realiza a escrita do objeto que o representa
	 * 
	 * @param output
	 */
	public abstract void writeToSource(CodeOutput output);

}
